package com.okunev.alertsample.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.okunev.alertsample.MainActivity
import org.koin.android.ext.android.inject

internal class AlertFirebaseMessagingService : FirebaseMessagingService() {

    private val dataStore by inject<AlertSampleTokenDataStore>()

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            startActivity(MainActivity.alertIntent(this))
        } catch (e: Exception) {
            // maybe we don't have permission
            Log.d("AlertSampleApp", "e - $e")
        }
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Log.d("AlertSampleApp", "onNewToken - $token")
        dataStore.saveToken(token)
        super.onNewToken(token)
    }
}
