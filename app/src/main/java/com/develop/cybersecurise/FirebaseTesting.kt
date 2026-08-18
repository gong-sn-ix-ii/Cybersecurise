package com.develop.cybersecurise

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database

class FirebaseTesting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebase_testing)

        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            // Write a message to the database
            println("click eiei")
            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World!")

        }


    }
}