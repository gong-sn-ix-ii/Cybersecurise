package com.develop.cybersecurise

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.databinding.ActivityMessageSmsBinding
import com.develop.cybersecurise.models.SMSMessageData
import com.develop.cybersecurise.services.AdapterMessageSMS
import com.develop.cybersecurise.services.SystemService
import org.w3c.dom.Text
import java.io.File

class MessageSMS : AppCompatActivity() {

    private lateinit var binding: ActivityMessageSmsBinding
    private var isDialogShown = false
    private var SenderCurrentForBlock:String = ""
    private var SenderCurrentForUnblock:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMessageSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedMessages: MutableMap<String, List<SMSMessageData>> = intent.getSerializableExtra("sms_messages") as MutableMap<String, List<SMSMessageData>>
        val keys: String = intent.getStringExtra("keys_sms") as String
        val isBlocking: Boolean = intent.getBooleanExtra("isBlocking", false)
        var status: String = ""

        val messages = selectedMessages[keys] ?: emptyList()

        messages.forEachIndexed { index, message ->
            Log.d("READ_SMS_ADAPTER", "Message $index status: ${message.status}")
            status = message.status.toString()
        }
        if (isBlocking){
            binding.blockSms.text = "Unblock"
            binding.blockSms.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        }else{
            binding.blockSms.text = "Block"
        }

        binding.blockSms.setOnClickListener {
            if (isBlocking){
                SenderCurrentForUnblock = keys
                showUnBlockingSMSAlertDialog(SenderCurrentForUnblock)
            }else{
                Log.d("Check SMS Blocking", "$isBlocking")
//                val blockedMessages = selectedMessages[keys]?.filter { it.status == status }
//
//                blockedMessages?.forEach { message ->
//                    blockMessage(message)
//                }
                SenderCurrentForBlock = keys
                showBlockingSMSAlertDialog(SenderCurrentForBlock)
                Log.d("Check Blick SMS List", "Sender Name: ${SenderCurrentForBlock}")
            }
        }

        val adapter = AdapterMessageSMS(this, selectedMessages)
        binding.listviewMessageSMS.adapter = adapter
        binding.messageDetect.text = "(${status})"

        when (status) {
            "OK" -> {
                binding.messageDetect.setTextColor(ContextCompat.getColor(this, R.color.Green))
                binding.messageName.setTextColor(ContextCompat.getColor(this, R.color.Green))
            }
            "scam" -> {
                binding.messageDetect.setTextColor(ContextCompat.getColor(this, R.color.red))
                binding.messageName.setTextColor(ContextCompat.getColor(this, R.color.red))
            }
            "spam" -> {
                binding.messageDetect.setTextColor(ContextCompat.getColor(this, R.color.Orange))
                binding.messageName.setTextColor(ContextCompat.getColor(this, R.color.Orange))
            }
            "OTP" -> {
                binding.messageDetect.setTextColor(ContextCompat.getColor(this, R.color.DodgerBlue))
                binding.messageName.setTextColor(ContextCompat.getColor(this, R.color.DodgerBlue))
            }
            else -> {
                binding.messageDetect.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.messageName.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        binding.messageName.text = keys
    }

    fun clearBlockList() {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(filesDir, fileName)

        if (file.exists()) {

            file.writeText("")

            Log.d("CLEAR_BLOCK", "All data has been cleared from the block list.")
        } else {
            Log.d("CLEAR_BLOCK", "Block list file does not exist.")
        }
    }

    fun blockMessage(senderBlocked: String) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(filesDir, fileName)

        val blockedSenders = if (file.exists()) file.readLines() else emptyList()

        if (!blockedSenders.contains(senderBlocked)) {

            file.appendText("$senderBlocked\n")
            Log.d("BLOCK_SMS", "Blocking message from sender: $senderBlocked")
        } else {
            Log.d("BLOCK_SMS", "Sender $senderBlocked is already blocked.")
        }
        readBlockList()
    }

    fun readBlockList() {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(filesDir, fileName)

        if (file.exists()) {
            val blockedSenders = file.readLines()
            blockedSenders.forEach { sender ->
                Log.d("Check Blick SMS List", "Blocked sender: $sender")
            }
        } else {
            Log.d("Check Blick SMS List", "No blocked senders found.")
        }
    }

    fun removeBlockedSender(senderToRemove: String) {
        val fileName = "CyberSecuriseBlockSMSList.txt"
        val file = File(filesDir, fileName)

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


    private fun showUnBlockingSMSAlertDialog(senderBlocked: String) {
        if (!isDialogShown) {
            val view = layoutInflater.inflate(R.layout.dialog_ask_unblocking_sms, null)

            val nameSenderTextView: TextView = view.findViewById(R.id.name_sender_sms)
            val unblockSubmit: Button = view.findViewById(R.id.unblocked_submit)
            val blockCancle: Button = view.findViewById(R.id.blocked_cancle)

            nameSenderTextView.text = senderBlocked

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(view)
            alertDialogBuilder.setCancelable(false) // Prevent user from canceling the dialog by pressing the back button
            val alertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false) // Prevent user from canceling the dialog by touching outside
            alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation_inUP_outDOWN
            alertDialog.show()

            isDialogShown = true

            unblockSubmit.setOnClickListener {
                SystemService().removeBlockedSender(this, senderBlocked)
                Toast.makeText(this, "Unblocked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ReadSMSActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

            blockCancle.setOnClickListener {
//                Toast.makeText(this, "Click cancel blocked", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss() // Close the dialog
                isDialogShown = false
            }

        } else {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.dismiss()
            isDialogShown = false
        }
    }

    private fun showBlockingSMSAlertDialog(senderBlocked: String) {
        if (!isDialogShown) {
            val view = layoutInflater.inflate(R.layout.dialog_ask_blocking_sms, null)

            val nameSenderTextView: TextView = view.findViewById(R.id.name_sender_sms)
            val unblockSubmit: Button = view.findViewById(R.id.unblocked_submit)
            val blockCancle: Button = view.findViewById(R.id.blocked_cancle)

            nameSenderTextView.text = senderBlocked

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(view)
            alertDialogBuilder.setCancelable(false) // Prevent user from canceling the dialog by pressing the back button
            val alertDialog = alertDialogBuilder.create()
            alertDialog.setCanceledOnTouchOutside(false) // Prevent user from canceling the dialog by touching outside
            alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation_inUP_outDOWN
            alertDialog.show()

            isDialogShown = true

            unblockSubmit.setOnClickListener {
                blockMessage(senderBlocked)
                Toast.makeText(this, "Blocked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ReadSMSActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

            blockCancle.setOnClickListener {
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss() // Close the dialog
                isDialogShown = false
            }

        } else {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.dismiss()
            isDialogShown = false
        }
    }

}
