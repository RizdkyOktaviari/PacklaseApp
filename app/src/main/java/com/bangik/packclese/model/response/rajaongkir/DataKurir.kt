package com.bangik.packclese.model.response.rajaongkir

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataKurir(
    @Expose
    @SerializedName("code")
    val code: String,
    @Expose
    @SerializedName("costs")
    val costs: List<Costs>,
    @Expose
    @SerializedName("name")
    val name: String
)