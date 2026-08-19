package com.develop.cybersecurise.services

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.develop.cybersecurise.models.MessageSMSModels
import com.google.gson.Gson
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class requestAPI_DetectSMS(private val context: Context) {
    fun getDetectionSMS(msg: String?, GETPOST: String = "GET", onComplete: (MessageSMSModels?) -> Unit) {
        Thread {
            try {
                var data: MessageSMSModels? = null
                val src = msg ?: ""
                val safeMsg = if (src.length > 500) src.substring(0, 500) else src
                val encodedMsg = URLEncoder.encode(safeMsg, "UTF-8")
                val url = URL("https://gong-sn-ix-ii-spam-scam-sms-detect.hf.space/msg=$encodedMsg")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = GETPOST
                connection.connectTimeout = 15000
                connection.readTimeout = 30000
                if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                    val request = Gson().fromJson(inputStreamReader, Map::class.java)

                    Log.d("API TESTING Detect SMS", "EIEI $request")

                    data = MessageSMSModels(
                        text = request["text"] as? String,
                        detect = request["detect"] as? String,
                        model = request["model"] as? String
                    )
                }

                if (data != null) {
                    Log.d("API TESTING Detect SMS", "condition have data $data")
                    onComplete(data)
                } else {
                    Log.d("API TESTING Detect SMS", "condition have [null] data $data")
                    onComplete(null)
                }
            } catch (e: Exception) {
                val handler = Handler(context.mainLooper)
                handler.post {
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.d("API TESTING Detect SMS ERROR", "${e.message}")
            }
        }.start()
    }
}