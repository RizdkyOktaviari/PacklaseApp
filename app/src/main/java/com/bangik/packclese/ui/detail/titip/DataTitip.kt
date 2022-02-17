package com.bangik.packclese.ui.detail.titip

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTitip(
    val dateStart: String,
    val dateEnd: String,
    val waktuTitip: String,
    val tambahanBox: Int,
    val quantity : String,
    val price: Int,
    val total: Int,
    val jenisTitip: String,
    val idTitip: String,
    val pictureTitip: String
) : Parcelable
