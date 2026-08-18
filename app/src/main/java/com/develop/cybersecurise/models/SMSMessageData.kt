package com.develop.cybersecurise.models

import android.os.Parcel
import android.os.Parcelable

data class SMSMessageData(
    val message: String,
    val sender: String,
    val type: Int,
    val date: Long,
    val read: Boolean,
    val thread: Int,
    val isSpam: Boolean,
    var status: String? = null,
    var isBlocking: Boolean = false  // Default value added for boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()  // Read boolean value
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(sender)
        parcel.writeInt(type)
        parcel.writeLong(date)
        parcel.writeByte(if (read) 1 else 0)
        parcel.writeInt(thread)
        parcel.writeByte(if (isSpam) 1 else 0)
        parcel.writeString(status)
        parcel.writeByte(if (isBlocking) 1 else 0)  // Write boolean value
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SMSMessageData> {
        override fun createFromParcel(parcel: Parcel): SMSMessageData {
            return SMSMessageData(parcel)
        }

        override fun newArray(size: Int): Array<SMSMessageData?> {
            return arrayOfNulls(size)
        }
    }
}
