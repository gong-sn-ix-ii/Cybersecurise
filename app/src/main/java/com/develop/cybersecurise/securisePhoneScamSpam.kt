package com.develop.cybersecurise

import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.develop.cybersecurise.databinding.ActivitySecuriseAccountBinding
import com.develop.cybersecurise.databinding.ActivitySecurisePhoneScamSpamBinding
import com.develop.cybersecurise.services.AdapterCheckgon
import com.develop.cybersecurise.services.requestAPIWithChaladohn
import com.develop.cybersecurise.services.requestAPIWithCheckgon

class securisePhoneScamSpam : AppCompatActivity() {

    private lateinit var binding: ActivitySecurisePhoneScamSpamBinding
    val dataFromAPI: MutableMap<String, Int?> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        val queryWithchaladohn = requestAPIWithChaladohn(this)
        val queryWithCheckgon = requestAPIWithCheckgon(this)

        var checkedModeChaladohn:String? = "phone"
        var checkedModeCheckgon:String? = "telephone"

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecurisePhoneScamSpamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Checkgon API Request = "telephone"

        val groups = mutableListOf<String>("accountNumber", "telephone", "shopLink")
        var bad:String = "9197109557"
        var good:String = "8493024022"

        binding.checkBtn.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.inputFormSearchAPI.windowToken, 0)
            var searchValueChaladohn: String? = binding.inputFormSearchAPI.text.toString()
            var searchValueCheckgon: String? = binding.inputFormSearchAPI.text.toString()
            binding.progress.visibility = View.VISIBLE
            binding.requestAPIData.visibility = View.GONE
            binding.requestAPIDataSeconds.visibility = View.GONE
            binding.listviewDetaCheckgon.visibility = View.GONE

//            Toast.makeText(this, "checkMode = ${checkedMode} and input = ${searchValueChaladohn}", Toast.LENGTH_LONG).show()

            queryWithchaladohn.queryDataFromAPI(mode = checkedModeChaladohn, numberID = searchValueChaladohn, GETPOST = "GET") { data ->

                dataFromAPI.putAll(data)

                if(dataFromAPI["searches"]==null && dataFromAPI["report"]==null && dataFromAPI["sources"]==null){
                    runOnUiThread {

                        binding.titleIdSearch.text = binding.inputFormSearchAPI.text
                        binding.searchCount.text = "ความเสี่ยงต่ำ"
                        binding.reportCount.text = "ยังไม่พบการร้องเรียน"
                        binding.sourcesCount.text = ""
                    }
                }else{
                    runOnUiThread {
                        binding.requestAPIData.visibility = View.VISIBLE
                        binding.layoutReport.visibility = View.VISIBLE
                        binding.layoutSources.visibility = View.VISIBLE

                        binding.titleIdSearch.text = binding.inputFormSearchAPI.text
                        binding.searchCount.text = "จำนวนการค้นหา  ${dataFromAPI["searches"].toString()}  ครั้ง"
                        binding.reportCount.text = "จำนวนการร้องเรียน  ${dataFromAPI["report"].toString()}  ครั้ง"
                        binding.sourcesCount.text = "พบข้อมูลการร้องเรียน  ${dataFromAPI["sources"].toString()}  แหล่ง"
                    }
                }

                queryWithCheckgon.queryAPIDataFromCheckgon(group = "${checkedModeCheckgon}", value = "${searchValueCheckgon}", "GET") { data ->
                    runOnUiThread {
                        if(data?.data==null){
                            Log.d("API TESTING [NULL]", "${data}")
                            binding.titleCheckgonID.text = binding.inputFormSearchAPI.text
                            binding.titleCheckgonSeverity.text = "ความเสี่ยงค่อนข้างต่ำ"
                            binding.titleCheckgonTotal.text = "โปรดพิจารณาก่อนตัดสินใจ"

                            binding.listviewDetaCheckgon.visibility = View.GONE
                            binding.requestAPIDataSeconds.visibility = View.VISIBLE
                            binding.requestAPIData.visibility = View.VISIBLE
                        }else{
                            Log.d("API TESTING [DATA]", "${data.data[0]?.dataSource}")
                            binding.titleCheckgonID.text = searchValueCheckgon
                            binding.titleCheckgonSeverity.text = if(data.severity=="high"){
                                "ความเสี่ยง สูงมาก"
                            }else if(data.severity=="medium"){
                                "ความเสี่ยง ปานกลาง"
                            }else{
                                "ความเสี่ยง ต่ำ"
                            }
                            binding.titleCheckgonTotal.text = "จำนวนการก่อเหตุ   ${data.total}  ครั้ง"
                            binding.listviewDetaCheckgon.visibility = View.VISIBLE
                            binding.listviewDetaCheckgon.adapter = AdapterCheckgon(this, data.data)

                            binding.listviewDetaCheckgon.visibility = View.VISIBLE
                            binding.requestAPIDataSeconds.visibility = View.VISIBLE
                            binding.requestAPIData.visibility = View.VISIBLE
                        }
                    }
                }
                runOnUiThread {
                    binding.progress.visibility = View.INVISIBLE
                }
            }

        }





    }
}