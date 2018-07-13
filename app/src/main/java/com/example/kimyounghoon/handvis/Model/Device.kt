package com.example.kimyounghoon.handvis.Model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by KIMYOUNGHOON on 3/30/2018.
 */
public  class Device() : Parcelable {

    var deviceNumber:Int = 0
    var fingerNumber:Int = 0
    var title:String = ""
    var background_image: String =""
    var switch:Boolean = false

    constructor(deviceNumber:Int,fingerNumber:Int, title:String,background_image:String,switch:Boolean) : this(){
        this.deviceNumber = deviceNumber
        this.fingerNumber = fingerNumber
        this.title = title
        this.background_image = background_image
        this.switch = switch
    }

        constructor(parcel: Parcel) : this() {
        deviceNumber = parcel.readInt()
        fingerNumber = parcel.readInt()
        title = parcel.readString()
        background_image = parcel.readString()
        switch = parcel.readByte() != 0.toByte()
    }


    override fun toString(): String {
        return title
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(deviceNumber)
        parcel.writeInt(fingerNumber)
        parcel.writeString(title)
        parcel.writeString(background_image)
        parcel.writeByte(if (switch) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Device> {
        override fun createFromParcel(parcel: Parcel): Device {
            return Device(parcel)
        }

        override fun newArray(size: Int): Array<Device?> {
            return arrayOfNulls(size)
        }
    }
}