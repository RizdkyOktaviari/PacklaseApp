package com.bangik.packclese.model.response.rajaongkir

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Costs(
    @Expose
    @SerializedName("cost")
    val cost: List<Cost>,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("service")
    val service: String
)