package com.develop.cybersecurise.models

import android.graphics.Bitmap

data class ContactData (
    val name: String,
    val phoneNumber: String,
    val imageResourceId: Bitmap?,
    val internationalPhoneNumber: String
)