package com.bangik.packclese.ui.detail.laundry

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.StringBufferInputStream

@Parcelize
data class DataLaundry(
    val price: Int,
    val tambahan: Int,
    val berat: Int,
    val jenisLaundry: String,
    val pictureLaundry: String,
    val idLaundry: String
): Parcelable