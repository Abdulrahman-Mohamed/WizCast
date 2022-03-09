package com.abdullrahman.weatherapp.model

import android.os.Parcel
import android.os.Parcelable

data class ModelLocation(val id:String,val lat:String, val lng:String, val address:String ="ss"):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lat)
        parcel.writeString(lng)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelLocation> {
        override fun createFromParcel(parcel: Parcel): ModelLocation {
            return ModelLocation(parcel)
        }

        override fun newArray(size: Int): Array<ModelLocation?> {
            return arrayOfNulls(size)
        }
    }

}
