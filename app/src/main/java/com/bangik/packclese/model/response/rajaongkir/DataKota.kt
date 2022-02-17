package com.bangik.packclese.model.response.rajaongkir

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataKota(
    @Expose
    @SerializedName("city_id")
    val city_id: String,
    @Expose
    @SerializedName("city_name")
    val city_name: String,
    @Expose
    @SerializedName("postal_code")
    val postal_code: String,
    @Expose
    @SerializedName("province")
    val province: String,
    @Expose
    @SerializedName("province_id")
    val province_id: String,
    @Expose
    @SerializedName("type")
    val type: String
): Parcelable