package com.develop.cybersecurise.services

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class requestAPIWithChaladohn(private val context: Context){
    fun queryDataFromAPI(mode:String?, numberID:String?, GETPOST:String="GET", onComplete: (Map<String, Int?>) -> Unit){
        Thread{
            var getValue = ArrayList<Int>()
            val data = mutableMapOf<String, Int?>()
            try{
                Log.d("API TESTING", "STARTED")
                val url = URL("https://www.chaladohn.com/report/${mode}/${numberID}")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = GETPOST
                Log.d("API TESTING", "GO TO CONDITION")
                if(connection.responseCode == HttpsURLConnection.HTTP_OK){

                    val inputSystem = connection.inputStream
                    val inputStreamReader =InputStreamReader(inputSystem, "UTF-8")
                    val regex = Regex("""\d+""")

                    BufferedReader(inputStreamReader).useLines { lines ->
                        lines.forEachIndexed { index, line ->
                            if (line.contains("<p class=\"color-new-primary\">", ignoreCase = true)) {
                                getValue.add((regex.find(line)!!.value).toInt())
                                Log.d("API TESTING ${index}", getValue.toString())
                            }
                        }
                    }
                }
                if(getValue.isEmpty() || getValue == null){
                    data["searches"] = null
                    data["report"] = null
                    data["sources"] = null
                }else{
                    data["searches"] = getValue.getOrElse(0) { null }
                    data["report"] = getValue.getOrElse(1) { null }
                    data["sources"] = getValue.getOrElse(2) { null }
                }
                Log.d("API TESTING", "ENDING")
                onComplete(data)
            }catch (e: Exception){
                val handler = Handler(context.mainLooper)
                handler.post {
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.d("API TESTING ERROR ", "${e.message}")
            }
        }.start()
    }
}