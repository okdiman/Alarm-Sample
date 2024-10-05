package com.okunev.alertsample.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.PowerManager

internal class AlertSamplePlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun startAlert() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build())
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
            val audioFileDescriptor = context.assets.openFd("alert.m4a")
            setDataSource(audioFileDescriptor.fileDescriptor, audioFileDescriptor.startOffset, audioFileDescriptor.length)
            audioFileDescriptor.close()
            isLooping = true
            prepare()
            start()
        }
    }

    fun stopAlert() {
        mediaPlayer?.let { player ->
            player.stop()
            player.release()
            mediaPlayer = null
        }
    }
}
