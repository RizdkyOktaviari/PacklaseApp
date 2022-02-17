package com.bangik.packclese.ui.detail.bersih

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBersih(
    val price: Int,
    val space: Int,
    val jenisBersih: String,
    val pictureBersih: String,
    val idBersih: String
): Parcelable
