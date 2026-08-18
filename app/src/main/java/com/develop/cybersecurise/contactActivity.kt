package com.develop.cybersecurise

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.develop.cybersecurise.databinding.ActivityContactBinding
import com.develop.cybersecurise.models.ContactData
import com.develop.cybersecurise.services.AdapterContact

class contactActivity : AppCompatActivity() {

    private val requestPermissionContact = 101
    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) >= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Loading Contact List", Toast.LENGTH_LONG).show()
            queryContractList()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), requestPermissionContact)
            Toast.makeText(this, "Permission not Granted", Toast.LENGTH_LONG).show()
            MainActivity()
        }

    }

    @SuppressLint("Range")
    fun queryContractList(){

        val contactList = mutableListOf<ContactData>()

        var cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
        )

        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val photo = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val internationalPhoneNumber = if (phoneNumber.startsWith("+")) {
                    phoneNumber
                } else {
                    "+66${phoneNumber.substring(1)}"
                }

                val photoBitmap: Bitmap? = if (photo != null) {
                    MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo))
                } else {
                    BitmapFactory.decodeResource(resources, R.drawable.photo)
                }
                val contact = ContactData(name, phoneNumber, photoBitmap, internationalPhoneNumber)
                contactList.add(contact)
            }
            it.close()
        }

        AdapterSetupValueContact(contactData = contactList)

    }

    fun AdapterSetupValueContact(contactData: List<ContactData>){
        binding.listviewContract.isClickable = true
        val adapter = AdapterContact(this, contactData)
        binding.listviewContract.adapter = adapter

        binding.listviewContract.setOnItemClickListener { parent, view, position, id ->
            val data = contactData[position]
            val intent = Intent(this, phoneCalling::class.java).apply {
                putExtra("callerName", data.name+"(more)")
                putExtra("callerNumber", data.phoneNumber)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestPermissionContact -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                    queryContractList()
                } else {
                    Toast.makeText(this, "Permission not Granted", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}