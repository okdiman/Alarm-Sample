package com.okunev.alertsample

import com.okunev.alertsample.drawoverlay.DrawOverlayManager
import com.okunev.alertsample.notification.AlertSampleTokenDataStore
import com.okunev.alertsample.player.AlertSamplePlayer
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val alertKoinModule = module {
    singleOf(::AlertSamplePlayer)
    factoryOf(::DrawOverlayManager)
    factoryOf(::AlertSampleTokenDataStore)
    single { androidApplication().getSharedPreferences("default", android.content.Context.MODE_PRIVATE) }
}
