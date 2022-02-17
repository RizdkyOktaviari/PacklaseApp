package com.bangik.packclese.ui.detail.paket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPaket(
    val total: String,
    val idPaket: String,
    val address: String,
    val origin: String,
    val destination: String,
    val weight: String,
    val courier: String,
    val ongkir:String,
    val jenisPaket:String,
    val picturePaket:String,
    val price:String,
    val etd:String
): Parcelable
