@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package ru.plumsoftware.avocado.ui.screen.food_scanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import ru.plumsoftware.avocado.R
import ru.plumsoftware.avocado.data.base.model.receipt.TypicalReceipt
import ru.plumsoftware.avocado.data.meal_scanner.ScannerDictionary
import ru.plumsoftware.avocado.data.shopping.ShoppingRepository
import ru.plumsoftware.avocado.data.user_preferences.UserPreferencesRepository
import ru.plumsoftware.avocado.ui.modifier.iosClickable
import ru.plumsoftware.avocado.ui.screen.AppDestination
import ru.plumsoftware.avocado.ui.screen.main.receipt.RecipesViewModel
import ru.plumsoftware.avocado.ui.screen.main.receipt.elements.SmallReceiptCard
import ru.plumsoftware.avocado.ui.screen.onboarding.IOSGreen
import ru.plumsoftware.avocado.ui.theme.Dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScannerScreen(
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository,
    shoppingRepository: ShoppingRepository
) {

    val recipesViewModel: RecipesViewModel = viewModel(
        factory = RecipesViewModel.Factory(
            userPrefsRepo = userPreferencesRepository,
            shoppingRepository = shoppingRepository
        )
    )
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // --- 1. РАЗРЕШЕНИЯ ---
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        hasCamPermission = it
    }
    LaunchedEffect(Unit) {
        if (!hasCamPermission) launcher.launch(Manifest.permission.CAMERA)
    }

    // --- 2. СОСТОЯНИЯ ---
    var isAnalyzing by remember { mutableStateOf(false) }
    var detectedFoodId by remember { mutableStateOf<String?>(null) }
    var detectedFoodNameRes by remember { mutableStateOf<Int?>(null) }
    var showResultSheet by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Контроллер камеры
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    val labeler = remember { ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS) }

    // --- 4. ЛОГИКА СКАНИРОВАНИЯ ---
    val scanImage: () -> Unit = {
        isAnalyzing = true
        errorMessage = null

        cameraController.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                @ExperimentalGetImage // Обязательная аннотация
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        // 🔥 ИСПРАВЛЕНИЕ 1: Создаем InputImage напрямую из mediaImage,
                        // передавая точный градус поворота от CameraX
                        val image = InputImage.fromMediaImage(
                            mediaImage,
                            imageProxy.imageInfo.rotationDegrees
                        )

                        labeler.process(image)
                            .addOnSuccessListener { labels ->
                                // Дебаг лог
                                Log.d("ML_KIT", "Вижу: ${labels.joinToString { "${it.text} (${it.confidence})" }}")

                                var matchedId: String? = null

                                for (label in labels) {
                                    val text = label.text.lowercase()
                                    // Игнорируем общие мусорные слова
                                    if (text == "food" || text == "plant" || text == "produce" ||
                                        text == "ingredient" || text == "fruit" || text == "vegetable" ||
                                        text == "still life photography") continue

                                    matchedId = ScannerDictionary.findFoodId(text)
                                    if (matchedId != null) break
                                }

                                if (matchedId != null) {
                                    detectedFoodId = matchedId
                                    detectedFoodNameRes = ScannerDictionary.getLocalizedName(matchedId)
                                    showResultSheet = true
                                } else {
                                    errorMessage = context.getString(R.string.search_no_results)
                                }
                            }
                            .addOnFailureListener {
                                errorMessage = "Ошибка нейросети"
                            }
                            .addOnCompleteListener {
                                // 🔥 ИСПРАВЛЕНИЕ 2: Обязательно закрываем ImageProxy в блоке complete,
                                // иначе камера зависнет после первого снимка!
                                isAnalyzing = false
                                imageProxy.close()
                            }
                    } else {
                        isAnalyzing = false
                        imageProxy.close()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    isAnalyzing = false
                    errorMessage = "Ошибка камеры: ${exception.message}"
                }
            }
        )
    }

    // --- 5. UI (Интерфейс Камеры) ---
    if (hasCamPermission) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            // Камера
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        controller = cameraController
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // ВЕРХНИЙ БАР
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = Dimen.medium, vertical = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .iosClickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = "Закрыть",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimen.medium))
                    Column {
                        Text(
                            text = "AI Сканер",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Наведите на продукт",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                // РАМКА ПО ЦЕНТРУ
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                    )

                    if (errorMessage != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(Dimen.medium))
                                .background(Color.Black.copy(alpha = 0.8f))
                                .padding(horizontal = Dimen.medium, vertical = Dimen.mediumHalf)
                        ) {
                            Text(text = errorMessage!!, color = Color.White)
                        }
                    }
                }

                // НИЖНЯЯ КНОПКА (ЗАГЛУШКА)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(bottom = 80.dp, top = Dimen.extraLarge),
                    contentAlignment = Alignment.Center
                ) {
                    if (isAnalyzing) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = IOSGreen)
                            Spacer(modifier = Modifier.height(Dimen.mediumHalf))
                            Text("Анализирую...", color = Color.White)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .border(4.dp, Color.White, CircleShape)
                                .padding(6.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .iosClickable { scanImage() }
                        )
                    }
                }
            }
        }
    } else {
        // Ошибка разрешения
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                Text("Разрешить камеру")
            }
        }
    }

    // --- 6. ШТОРКА РЕЗУЛЬТАТОВ ---
    if (showResultSheet && detectedFoodId != null && detectedFoodNameRes != null) {
        val matchedRecipes = remember(detectedFoodId) {
            recipesViewModel.recipeList.value.filter { it.relatedFood.contains(detectedFoodId) }
        }

        ScannerResultSheet(
            foodNameRes = detectedFoodNameRes!!,
            foodId = detectedFoodId!!,
            recipes = matchedRecipes,
            onDismiss = { showResultSheet = false },
            onRecipeClick = { id ->
                showResultSheet = false
                navController.navigate(AppDestination.ReceiptDetailRoute(id))
            },
            onFoodClick = { id ->
                showResultSheet = false
                navController.navigate(AppDestination.DetailedScreen(id))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerResultSheet(
    foodNameRes: Int,
    foodId: String,
    recipes: List<TypicalReceipt>,
    onDismiss: () -> Unit,
    onRecipeClick: (String) -> Unit,
    onFoodClick: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimen.extraLarge)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.large),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Я вижу: ${stringResource(foodNameRes)}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { onFoodClick(foodId) }) {
                    Text("О продукте", color = IOSGreen, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(Dimen.medium))

            if (recipes.isNotEmpty()) {
                Text(
                    text = "Что можно приготовить:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        start = Dimen.large,
                        end = Dimen.large,
                        bottom = Dimen.mediumHalf
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimen.large),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.medium)
                ) {
                    items(recipes) { receipt ->
                        SmallReceiptCard(
                            receipt = receipt,
                            onClick = { onRecipeClick(receipt.id) }
                        )
                    }
                }
            }
        }
    }
}