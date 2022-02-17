package com.bangik.packclese.model.response.checkout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckoutLaundryResponse(
    @Expose
    @SerializedName("address")
    val address: String,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("extra")
    val extra: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("payment_url")
    val payment_url: String,
    @Expose
    @SerializedName("service_id")
    val service_id: String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("subtotal")
    val subtotal: String,
    @Expose
    @SerializedName("total")
    val total: String,
    @Expose
    @SerializedName("transaction_id")
    val transaction_id: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("user_id")
    val user_id: String,
    @Expose
    @SerializedName("weight")
    val weight: String
)