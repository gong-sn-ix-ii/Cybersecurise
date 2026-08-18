@file:Suppress("DEPRECATION")
package com.develop.cybersecurise

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class phoneCalling : AppCompatActivity() {

    private val requestPermissionContact = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), requestPermissionContact)
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), requestPermissionContact)
            }
        }else{
            //wait command
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==101){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show()
            }
            return
        }
    }
}