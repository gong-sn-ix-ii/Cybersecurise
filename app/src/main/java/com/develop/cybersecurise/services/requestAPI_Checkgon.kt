    package com.develop.cybersecurise.services

    import android.content.Context
    import android.os.Handler
    import android.util.Log
    import android.widget.Toast
    import com.develop.cybersecurise.models.ScammerData
    import com.develop.cybersecurise.models.ScammerModels
    import com.google.gson.Gson
    import java.io.InputStreamReader
    import java.lang.Exception
    import java.net.HttpURLConnection
    import java.net.URL
    import javax.net.ssl.HttpsURLConnection

    class requestAPIWithCheckgon(private val context: Context) {
        fun queryAPIDataFromCheckgon(group:String?, value:String?, GETPOST:String="GET", onComplete: (ScammerModels?) -> Unit){
            Thread{
                var getValue = ArrayList<Int>()
                try {
                    var data: ScammerModels? = null
                    val url = URL("https://api.checkgon.com/api/watchList/list?limit=1&page=1&filterKey=${group},status&filterVal=${value},Activate&censor=False")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = GETPOST
                    if(connection.responseCode == HttpsURLConnection.HTTP_OK){
                        val inputSystem = connection.inputStream
                        val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                        val request = Gson().fromJson(inputStreamReader, Map::class.java)

                        val dataList: List<Map<String, Any>>? = request["data"] as? List<Map<String, Any>>
                        Log.d("API TESTING CHECKGON", "${(request["data"] as? List<*>)?.size}")

                        data = if (dataList?.isEmpty() == true) {
                            ScammerModels()
                        } else {
                            val scammerDataList: List<ScammerData> = dataList!!.map{ map ->
                                ScammerData(
                                    bank_to_abbr = map["bank_to_abbr"] as? String,
                                    bank_to_acct = map["bank_to_acct"] as? String,
                                    createdTime = map["createdTime"] as? String,
                                    dataSource = map["dataSource"] as? String,
                                    status = map["status"] as? String
                                )
                            }

                            Log.d("API TESTING CHECKON", "EIEI ${request}")
                            ScammerModels(
                                severity = request["case_severity"]?.toString(),
                                data = scammerDataList,
                                page = (request["lastPage"] as? Double)?.toInt(),
                                status = (request["status"] as? Double)?.toInt(),
                                total = (request["total"] as? Double)?.toInt(),
                            )
                        }
                        if (data != null) {
                            Log.d("API TESTING CHECKON", "condition have data ${data}")
                            onComplete(data)
                        } else {
                            Log.d("API TESTING CHECKON", "condition have [null] data ${data}")
                            onComplete(null)
                        }
                    }
                } catch (e: Exception){
                    val handler = Handler(context.mainLooper)
                    handler.post {
                        Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    Log.d("API TESTING CHECKON ERROR", "${e.message}")
                }
            }.start()
        }
    }