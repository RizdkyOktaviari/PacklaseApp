package com.bangik.packclese.model.response.rajaongkir

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Cost(
    @Expose
    @SerializedName("etd")
    val etd: String,
    @Expose
    @SerializedName("note")
    val note: String,
    @Expose
    @SerializedName("value")
    val value: Int
)