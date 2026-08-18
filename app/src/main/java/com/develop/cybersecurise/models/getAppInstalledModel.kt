package com.develop.cybersecurise.models

import android.graphics.drawable.Drawable

data class AppInstalledData(
    val icon:Drawable,
    val label:String,
    val permission:MutableList<String>,
    val isEnable:Boolean,
    val isPlaystore:Boolean,
    val riskLevel:Double,
)