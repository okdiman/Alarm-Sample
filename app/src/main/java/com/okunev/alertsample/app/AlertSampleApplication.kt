package com.okunev.alertsample.app

import android.app.Application
import com.okunev.alertsample.alertKoinModule
import com.okunev.alertsample.notification.AlertSampleNotificationChannels
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class AlertSampleApplication : Application() {
    private val channels by inject<AlertSampleNotificationChannels>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlertSampleApplication)
            modules(alertKoinModule)
        }
        channels.create()
    }
}
