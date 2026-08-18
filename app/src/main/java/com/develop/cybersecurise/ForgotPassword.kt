package com.develop.cybersecurise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        val btn_login = findViewById<Button>(R.id.login)
        val btn_repass = findViewById<Button>(R.id.repass)

        btn_login.setOnClickListener {
            Toast.makeText(this, "Login User", Toast.LENGTH_LONG).show()
            val Intent = Intent(this, Login::class.java)
            startActivity(Intent)
        }

        btn_repass.setOnClickListener {
            val Intent = Intent(this, ForgotPassword::class.java)
            startActivity(Intent)
        }

    }
}