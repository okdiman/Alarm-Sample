package com.okunev.alertsample.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.okunev.alertsample.MainActivity
import com.okunev.alertsample.R
import com.okunev.alertsample.player.AlertSamplePlayer
import org.koin.android.ext.android.inject

internal class AlertFirebaseMessagingService : FirebaseMessagingService() {

    private val alertPlayer by inject<AlertSamplePlayer>()

    override fun onMessageReceived(message: RemoteMessage) {
        alertPlayer.startAlert()
        showAlertNotification()
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        Log.d("AlertSampleApp", "onNewToken - $token")
        super.onNewToken(token)
    }

    @SuppressLint("MissingPermission")
    private fun showAlertNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, AlertSampleNotificationChannels.ALARM_CHANNEL_ID)
            .setContentTitle("You phone is ringing!")
            .setContentText("Press here to turn off the alarm")
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
    }

    private companion object {
        const val PENDING_INTENT_REQUEST_CODE = 7777
        const val NOTIFICATION_ID = 204
    }
}
