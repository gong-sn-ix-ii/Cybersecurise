package com.develop.cybersecurise.services

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import java.io.File
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class SystemService {

    val appNameFromPlaystore = arrayOf(
        "TikTok", "Facebook", "Instagram", "WhatsApp", "Messenger", "Snapchat", "Twitter", "Netflix", "Spotify",
        "Microsoft Teams", "Google Chrome", "Gmail", "Google Maps", "YouTube", "Google Drive", "Google Photos",
        "Google Meet", "Amazon Shopping", "Grab", "Netflix Party", "Disney+", "Twitch", "Reddit", "Discord", "Dropbox",
        "Among Us", "Clash of Clans", "Clash Royale", "Candy Crush Saga", "PUBG MOBILE", "Garena Free Fire", "Subway Surfers",
        "Roblox", "Fortnite", "Pokémon GO", "Mobile Legends", "Call of Duty", "Honkai: Star Rail", "Honkai Impact 3",
        "กยศ. Connect", "TFT", "KFC", "MyMo", "Google Play Store", "Major", "Azur Lane", "Google Drive", "นาฬิกา", "Line", "Fastprint", "Messenger", "NEXT", "Teams", "เป๋าตัง",
        "KTB"
    )

    val specifyName = arrayOf("google", "line", "honkai", "นาฬิกา", "microsft", "true", "Steam", "honkai", "shopee", "facebook")

    fun formatDateSMS(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    fun scanAppNameInPlaystore(appName: String): Boolean {
        return appName in appNameFromPlaystore || specifyName.any { it.equals(appName.lowercase(), ignoreCase = true) }
    }

    fun generateRandomString(length: Int): String {
        val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val random = Random

        return (1..length)
            .map { characters[random.nextInt(characters.size)] }
            .joinToString("")
    }

    fun messageSMSBlockList(context: Context, senderBlocking: String): Boolean {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(context.filesDir, fileName)
        var isBlocking:Boolean = false

        if (file.exists()) {
            val blockedSenders = file.readLines()
            blockedSenders.forEach { sender ->
                if (senderBlocking == sender) {
                    isBlocking = true
                }
                Log.d("Mumi Desuka Check Block SMS", "Blocked sender: $sender")
            }
        } else {
            Log.d("Mumi Desuka Check Block SMS", "No blocked senders found.")
        }

        return isBlocking
    }

    fun clearBlockList(context: Context) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(context.filesDir, fileName)

        if (file.exists()) {
            // เขียนข้อมูลเปล่าลงไปในไฟล์เพื่อล้างข้อมูลทั้งหมด
            file.writeText("")

            Log.d("CLEAR_BLOCK", "All data has been cleared from the block list.")
        } else {
            Log.d("CLEAR_BLOCK", "Block list file does not exist.")
        }
    }

    fun readBlockList(context: Context) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(context.filesDir, fileName)

        if (file.exists()) {
            val blockedSenders = file.readLines()
            blockedSenders.forEach { sender ->
                Log.d("Check Blick SMS List", "Blocked sender: $sender")
            }
        } else {
            Log.d("Check Blick SMS List", "No blocked senders found.")
        }
    }

    fun removeBlockedSender(context: Context,senderToRemove: String) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(context.filesDir, fileName)

        if (file.exists()) {
            val blockedSenders = file.readLines().toMutableList()

            if (blockedSenders.contains(senderToRemove)) {
                blockedSenders.remove(senderToRemove)

                file.writeText(blockedSenders.joinToString("\n"))

                Log.d("REMOVE_BLOCK", "$senderToRemove has been removed from the block list.")
            } else {
                Log.d("REMOVE_BLOCK", "$senderToRemove was not found in the block list.")
            }
        } else {
            Log.d("REMOVE_BLOCK", "Block list file does not exist.")
        }
    }
}



//package com.develop.cybersecurise.services
//
//import android.icu.text.SimpleDateFormat
//import android.util.Log
//import java.io.File
//import java.util.Date
//import java.util.Locale
//import kotlin.random.Random
//
//class SystemService {
//
//    val appNameFromPlaystore = arrayOf(
//        "TikTok", "Facebook", "Instagram", "WhatsApp", "Messenger", "Snapchat", "Twitter", "Netflix", "Spotify",
//        "Microsoft Teams", "Google Chrome", "Gmail", "Google Maps", "YouTube", "Google Drive", "Google Photos",
//        "Google Meet", "Amazon Shopping", "Grab", "Netflix Party", "Disney+", "Twitch", "Reddit", "Discord", "Dropbox" ,
//        "Among Us", "Clash of Clans", "Clash Royale", "Candy Crush Saga", "PUBG MOBILE", "Garena Free Fire", "Subway Surfers",
//        "Roblox", "Fortnite", "Pokémon GO", "Mobile Legends", "Call of Duty","Honkai: Star Rail", "Honkai Impact 3",
//        "กยศ. Connect", "TFT", "KFC", "MyMo", "Google Play Store", "Major", "Azur Lane", "Google Drive", "นาฬิกา", "Line", "Fastprint", "Messenger", "NEXT", "Teams", "เป๋าตัง",
//        "KTB",
//        )
//
//    val specifyName = arrayOf("google", "line", "honkai", "นาฬิกา", "microsft", "true", "Steam", "honkai", "shopee", "facebook")
//
//    fun formatDateSMS(timestamp: Long):String{
//
//        val date = Date(timestamp)
//        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val formattedDate: String = sdf.format(date)
//
//
//        return formattedDate
//    }
//
//    fun scanAppNameInPlaystore(appName: String): Boolean{
//        if(appName in appNameFromPlaystore || specifyName.any { it.equals(appName.lowercase(), ignoreCase = true) }){
//            return true
//        }else{
//            return false
//        }
//    }
//
//    fun generateRandomString(length: Int): String {
//        val characters = ('A'..'Z') + ('a'..'z') + ('0'..'9')
//        val random = Random
//
//        return (1..length)
//            .map { characters[random.nextInt(0, characters.size)] }
//            .joinToString("")
//    }
//
//    fun MessageSMSBlockList(senderBlocking:String):Boolean {
//        val fileName = "CyberSecuriseBlockSMSList.txt"
//        val file = File(filesDir, fileName)
//        var isBlocking:Boolean = false
//
//        if (file.exists()) {
//            val blockedSenders = file.readLines()
//            blockedSenders.forEach { sender ->
//                if(senderBlocking==sender){
//                    isBlocking = true
//                }
//                Log.d("Mumi Desuka Check Block SMS", "Blocked sender: $sender")
//            }
//        } else {
//            Log.d("Mumi Desuka Check Block SMS", "No blocked senders found.")
//        }
//
//        return isBlocking
//    }
//
//}