package com.okunev.alertsample.app

import android.app.Application
import com.okunev.alertsample.alertKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class AlertSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlertSampleApplication)
            modules(alertKoinModule)
        }
    }
}
