package com.okunev.alertsample.notification

import android.content.SharedPreferences

internal class AlertSampleTokenDataStore(private val sharedPreferences: SharedPreferences) {
    fun saveToken(token: String) = sharedPreferences.edit().putString(PUSH_TOKEN_KEY, token).commit()
    fun getToken() = sharedPreferences.getString(PUSH_TOKEN_KEY, "") ?: ""

    private companion object {
        const val PUSH_TOKEN_KEY = "push_token"
    }
}
