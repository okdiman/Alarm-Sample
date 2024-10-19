package com.okunev.alertsample.drawoverlay

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

internal class DrawOverlayManager {
    fun request(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
            data = Uri.fromParts("package", (context).packageName, null)
        }
        context.startActivity(intent)
    }

    fun isGranted(context: Context) = Settings.canDrawOverlays(context)
}
