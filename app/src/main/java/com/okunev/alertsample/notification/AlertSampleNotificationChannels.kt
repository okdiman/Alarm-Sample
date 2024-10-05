package com.okunev.alertsample.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat

internal class AlertSampleNotificationChannels(private val context: Context) {

    fun create() {
        val name = "Alarm"
        val channelDescription = "Alarm notifications"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(ALARM_CHANNEL_ID, name, priority).apply {
            enableVibration(true)
            enableLights(true)
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    companion object {
        const val ALARM_CHANNEL_ID = "alarm_notifications"
    }
}
