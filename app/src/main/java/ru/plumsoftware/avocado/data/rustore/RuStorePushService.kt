package ru.plumsoftware.avocado.data.rustore

import ru.rustore.sdk.pushclient.messaging.model.RemoteMessage
import ru.rustore.sdk.pushclient.messaging.exception.RuStorePushClientException
import ru.rustore.sdk.pushclient.messaging.service.RuStoreMessagingService

class RuStorePushService : RuStoreMessagingService() {

    override fun onNewToken(token: String) {
    }

    override fun onMessageReceived(message: RemoteMessage) {
    }

    override fun onDeletedMessages() {
    }

    override fun onError(errors: List<RuStorePushClientException>) {
    }
}