package com.bangik.packclese.model.response.rajaongkir

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("province_id")
    val province_id: Int,
    @Expose
    @SerializedName("province")
    val province: String
): Parcelable