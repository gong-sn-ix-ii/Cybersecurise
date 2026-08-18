package com.develop.cybersecurise

// callHistory.kt

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.databinding.ActivityCallHistoryBinding
import com.develop.cybersecurise.models.callHistoryModel
import com.develop.cybersecurise.services.AdapterCallHistory
import com.develop.cybersecurise.services.SystemService

class callHistory : AppCompatActivity() {


    private lateinit var binding:ActivityCallHistoryBinding

    private val TAG = "Mumi Desuka"
    private val REQUEST_READ_CALL_LOG = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALL_LOG),
                REQUEST_READ_CALL_LOG
            )
        } else {
            readCallLog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CALL_LOG) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readCallLog()
            } else {
                Log.d(TAG, "Permission denied for reading call log")
            }
        }
    }

    @SuppressLint("Range")
    private fun readCallLog() {
        val dataCallHistory: MutableList<callHistoryModel> = mutableListOf()
        val callLogMap = HashMap<String, Int>()

        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            "${CallLog.Calls.DATE} DESC"
        )

        cursor?.use { c ->
            var count = 0
            while (c.moveToNext() && count < 50) {
                count++
                val name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME)) ?: ""
                val number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) ?: ""
                val date = c.getLong(c.getColumnIndex(CallLog.Calls.DATE))
                val formattedDate = SystemService().formatDateSMS(date)
                val type = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE))
                val callType = when (type) {
                    CallLog.Calls.INCOMING_TYPE -> "Incoming"
                    CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
                    CallLog.Calls.MISSED_TYPE -> "Missed"
                    else -> "Unknown"
                }

                if (callLogMap.containsKey(number)) {
                    callLogMap[number] = callLogMap[number]!! + 1
                } else {
                    callLogMap[number] = 1
                }

                val callHistoryItem = callHistoryModel(name, number, formattedDate, callLogMap[number]!!.toInt(), callType)
                dataCallHistory.add(callHistoryItem)
            }
        }

        cursor?.close()

        for (item in dataCallHistory) {
            Log.d(TAG, "Call History - Name: ${item.name}, Number: ${item.phoneNumber}, Date: ${item.date}, Count: ${item.stack}, Type: ${item.Type}")
        }

        binding.listviewCallHistory.isClickable = true
        val adapter = AdapterCallHistory(this, dataCallHistory)
        binding.listviewCallHistory.adapter = adapter
        binding.listviewCallHistory.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

    }

}

