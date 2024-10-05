package com.okunev.alertsample

import com.okunev.alertsample.notification.AlertSampleNotificationChannels
import com.okunev.alertsample.player.AlertSamplePlayer
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val alertKoinModule = module {
    singleOf(::AlertSamplePlayer)
    factoryOf(::AlertSampleNotificationChannels)
}
