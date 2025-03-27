package com.pirrera.tvshelf.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseMessagingService", "Refreshed token: $token")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("🔥 FCM", "Message reçu !")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("🔥 FCM", "Data: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d("🔥 FCM", "Notif: ${it.body}")
        }
    }

    private fun sendRegistrationToServer(token: String) {
        // Implement your logic to send the token to your server here
        // ...
    }
}