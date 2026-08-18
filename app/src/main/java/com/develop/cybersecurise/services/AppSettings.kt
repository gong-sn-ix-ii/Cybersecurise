package com.conding.cybersecurise.services

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

class AppSettings {
    companion object{
        fun isAccessibilityEnabled(context: Context): Boolean{
            val accessibility = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            return accessibility.isEnabled
        }

        fun isMockLocationEnabled(context: Context): Boolean {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ALLOW_MOCK_LOCATION) != "0"
        }

        fun isDevelopmentSettingsEnabled(context: Context): Boolean {
            return Settings.Global.getInt(context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0
        }

        fun isWifiWatchdogOn(context: Context): Boolean {
            return Settings.Global.getInt(context.contentResolver, Settings.Global.WIFI_WATCHDOG_ON, 0) != 0
        }

        fun getDefaultInputMethod(context: Context): String? {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        }

        fun isOverlayPermissionGranted(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return Settings.canDrawOverlays(context)
            }
            return true
        }
    }
}