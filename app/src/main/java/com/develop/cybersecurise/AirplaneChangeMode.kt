package com.develop.cybersecurise

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirplaneChangeMode:  BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action== Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val isTurned = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            println("Is airplane mode show ${isTurned}")
            Toast.makeText(context, "status: $isTurned", Toast.LENGTH_SHORT).show()
        }
    }
}