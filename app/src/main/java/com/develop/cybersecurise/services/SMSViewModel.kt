package com.develop.cybersecurise.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develop.cybersecurise.models.MessageSMSModels
import com.develop.cybersecurise.models.SMSMessageData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SmsViewModel : ViewModel() {
    val apiResponse = MutableLiveData<Map<String, MessageSMSModels>>()
    val errorMessage = MutableLiveData<String?>()

    fun fetchDataFromApi(allMessage: Map<String, List<SMSMessageData>>) {
        viewModelScope.launch {
            val results = mutableMapOf<String, MessageSMSModels>()
            try {
                withContext(Dispatchers.IO) {
                    for ((sender, messages) in allMessage) {
                        var msg = messages.joinToString(separator = " ") { it.message }
                        Log.d("Check msg To API", "$sender | $msg")

                        // Reduce multiple spaces to a single space
                        msg = msg.replace(Regex("\\s+"), " ")

                        // Remove special characters # / \
                        msg = msg.replace(Regex("[#\\\\/]"), "")

                        Log.d("Processed msg To API", "$sender | $msg")

                        val response = fetchDetectionSMS(msg)
                        if (response != null) {
                            results[sender] = response
                        }
                    }
                }

                // Update status in allMessage based on API response
                results.forEach { (sender, detectResult) ->
                    allMessage[sender]?.forEach { smsMessageData ->
                        smsMessageData.status = detectResult.detect
                    }
                }

                // Notify observers with updated data
                apiResponse.value = results
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    private fun fetchDetectionSMS(msg: String): MessageSMSModels? {
        Log.d("Check Sender SMS and Message ALL", "MSG = $msg")
        val safeMsg = if (msg.length > 500) msg.substring(0, 500) else msg
        val encodedMsg = URLEncoder.encode(safeMsg, "UTF-8")
        val url = URL("https://gong-sn-ix-ii-spam-scam-sms-detect.hf.space/msg=$encodedMsg")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 15000
        connection.readTimeout = 30000
        return if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val request = Gson().fromJson(inputStreamReader, Map::class.java)
            Log.d("Check msg To API", "Detect ${request["detect"] as? String}")
            MessageSMSModels(
                text = request["text"] as? String,
                detect = request["detect"] as? String,
                model = request["model"] as? String
            )
        } else {
            null
        }
    }
}