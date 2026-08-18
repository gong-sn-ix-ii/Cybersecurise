package com.develop.cybersecurise

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager
import android.widget.Toast

class PhoneStateService : Service() {

    private val phoneStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val state: String? = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state != null) {
                when (state) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        Toast.makeText(context, "Ringing service!", Toast.LENGTH_SHORT).show()
                    }
                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                        Toast.makeText(context, "Received service", Toast.LENGTH_SHORT).show()
                    }
                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        Toast.makeText(context, "IDLE service", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(phoneStateReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(phoneStateReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}