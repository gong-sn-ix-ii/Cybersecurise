package com.develop.cybersecurise

import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class getFileFromStorage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_file_from_storage)






//        val filePath:String = "Download/apk"
//        if (FileTypeChecker.isAPKFile(filePath)) {
//            Log.d("Test File directory", "The file is an APK file.")
//        } else {
//            Log.d("Test File directory", "The file is not an APK file.")
//        }
//


//        Log.d("Directory External", "${Environment.getExternalStorageDirectory()}")
//        val folder: File = Environment.getExternalStorageDirectory()
//        Log.d("Directory External", "folder => ${folder}")

//        val files = ArrayList<String>()
//        queryFile(folder, files)
//        files.forEach { filePath ->
//            Log.d("File Path", filePath)
//        }

        val file:File = File(Environment.getExternalStoragePublicDirectory("Download"), "/")
        Log.d("Directory ==> ", "${file.listFiles()}")

        getDataDirectory(this)

    }


    object FileTypeChecker {
        fun isAPKFile(filePath: String?): Boolean {
            val file = File(filePath)
            val fileName = file.name
            val fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1)
            return fileExtension.equals("apk", ignoreCase = true)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val filePath = "Download/"
            if (isAPKFile(filePath)) {
                println("The file is an APK file.")
            } else {
                println("The file is not an APK file.")
            }
        }
    }


    private fun getDataDirectory(context:Context):List<String>{
        val images = mutableListOf<String>()
        val contentResolver:ContentResolver = context.contentResolver

        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor.use { cursor ->
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()){
                val imagePath = cursor.getString(columnIndex)
                images.add(imagePath)
            }


        }
        Log.d("Testing Get Image", "${images}")

        return images
    }


//    private fun queryFile(dir: File, fileList: ArrayList<String>) {
//        val listFile = dir.listFiles()
//        if (listFile != null) {
//            for (file in listFile) {
//                if (file.isDirectory) {
//                    queryFile(file, fileList)
//                } else {
//                    fileList.add(file.absolutePath)
//                }
//                Log.d("Directory External", "${file}")
//            }
//        }
//    }



}