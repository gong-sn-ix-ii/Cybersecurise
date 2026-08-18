package com.develop.cybersecurise

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.databinding.ActivityAppInstalledDetailsBinding
import com.develop.cybersecurise.services.AdapterAppInstalledDetail

class AppInstalledDetails : AppCompatActivity() {

    private lateinit var binding : ActivityAppInstalledDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppInstalledDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val label = intent.getStringExtra("label")
        val iconResourceId = intent.getIntExtra("icon", R.drawable.logo)
        val permissions = intent.getStringArrayExtra("permission")?.toMutableList()

        val iconDrawable = ContextCompat.getDrawable(this, iconResourceId)
        binding.iconApp.setImageDrawable(iconDrawable)

        binding.appName.text = label

        val adapter = AdapterAppInstalledDetail(this, permissions ?: mutableListOf())
        Log.d("Permission In Smart Clean", "${permissions}")
        binding.listView.adapter = adapter




    }
}