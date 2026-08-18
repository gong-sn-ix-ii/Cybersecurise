package com.develop.cybersecurise

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class applicationQueryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_application_query)

        val appList: ArrayList<String> = installedApps()
        val pathFile: List<String>  = getAllStoragePaths(this)
        val externalStorageDir = Environment.getExternalStorageDirectory()
        val fileList = mutableListOf<String>()

        val pm = packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (app in apps) {
            val installerPackageName = pm.getInstallerPackageName(app.packageName)
            if (installerPackageName == "com.android.vending") {
                println("${app.loadLabel(pm)}: ติดตั้งจาก Play Store")
                Log.d("App Installed Play Store", "${app.loadLabel(pm)}")
            } else {
                Log.d("App Installed Play Store Not", "${app.loadLabel(pm)}")
            }
        }

        val filesDir = this.filesDir
        val files = filesDir.listFiles()
        Log.d("app installed List", "dir ${filesDir} and ${files} external => ${externalStorageDir}")
        Log.d("App installed List APK Search", "path File = ${pathFile}")

        for (appName in appList) {
            println("Application: $appName")
            Log.d("Application Test APP Permission", "name app${appName}")
            val permissions = getAppPermissions(appName)
            if (permissions != null) {
                Log.d("Application Test APP Permission", "Permission:")
                for (permission in permissions) {
                    Log.d("Application Test APP Permission", "-- ${permission}")
                }
            } else {
                Log.d("Application Test APP Permission", "-- No Permission")
            }
        }

        val allFiles = getAllFilesFromDirectory(Environment.getExternalStorageDirectory()?.absolutePath ?: "")
        for (file in allFiles) {
            Log.d("Doraemon", "${file}")
        }



    }


    fun getAllFilesFromDirectory(directoryPath: String): List<File> {
        val externalStorageDirectory: File? = Environment.getExternalStorageDirectory()
        val files: MutableList<File> = mutableListOf()

        externalStorageDirectory?.let { externalDir ->
            if (externalDir.exists() && externalDir.isDirectory) {
                externalDir.listFiles()?.let { fileList ->
                    for (file in fileList) {
                        if (file.isFile) {
                            files.add(file)
                        } else if (file.isDirectory) {
                            files.addAll(getAllFilesFromDirectory(file.absolutePath))
                        }
                    }
                }
            }
        }

        return files
    }


    fun getAppPermissions(packageName: String): Array<String>? {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            return packageInfo.requestedPermissions
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun isExternalStorageAvailable(): Boolean {
        val state = Environment.getExternalStorageState()
        return state == Environment.MEDIA_MOUNTED
    }


    private fun installedApps(): ArrayList<String> {
        val packageManager = packageManager
        val installedApplication = ArrayList<String>()
        val packages = packageManager.getInstalledPackages(0)
        for(packageInfo in packages){
            val appname = packageInfo.applicationInfo.loadLabel(packageManager).toString()
            installedApplication.add(appname)
        }
        return installedApplication
    }

}

private fun getAllStoragePaths(context: Context): List<String> {
    val storagePaths = mutableListOf<String>()

    val externalStoragePath = Environment.getExternalStorageDirectory().absolutePath
    storagePaths.add(externalStoragePath)

    val internalStoragePath = context.filesDir.absolutePath
    storagePaths.add(internalStoragePath)

    return storagePaths
}

