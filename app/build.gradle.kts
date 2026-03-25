plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)

    // Serialization
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.protobuf") version "0.9.6"

    // KSP
    id("com.google.devtools.ksp")

    // Google services
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "ru.plumsoftware.avocado"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "ru.plumsoftware.avocado"
        minSdk = 24
        targetSdk = 36
        versionCode = 4
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("int", "PLATFORM", "1")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
//    kotlin {
//        jvmToolchain(11)
//    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    lintOptions {
        disable += "MobileAdsSdkOutdatedVersion"
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.32.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
                create("kotlin")
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.foundation.layout)

    // Nav3
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.androidx.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.protobuf.kotlin.lite)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Proto data store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)

    // AndroidX Palette
    implementation(libs.androidx.palette.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Ads
    implementation(libs.mobileads)

    // Worker
    implementation(libs.androidx.work.runtime.ktx)

    // Firebase
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
    implementation(libs.firebase.config)
}

configurations.all {
    exclude(group = "com.google.firebase", module = "protolite-well-known-types")
}