package com.bangik.packclese.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTransaction(
    @Expose
    @SerializedName("address")
    val address: String,
    @Expose
    @SerializedName("address_detail")
    val address_detail: String,
    @Expose
    @SerializedName("courier")
    val courier: String,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at: String,
    @Expose
    @SerializedName("destination")
    val destination: String,
    @Expose
    @SerializedName("end")
    val end: String,
    @Expose
    @SerializedName("extra")
    val extra: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("origin")
    val origin: String,
    @Expose
    @SerializedName("quantity")
    val quantity: Int,
    @Expose
    @SerializedName("service")
    val service: Service,
    @Expose
    @SerializedName("service_id")
    val service_id: Int,
    @Expose
    @SerializedName("space")
    val space: String,
    @Expose
    @SerializedName("start")
    val start: String,
    @Expose
    @SerializedName("subtotal")
    val subtotal: Int,
    @Expose
    @SerializedName("transaction_id")
    val transaction_id: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("voucher_code")
    val voucher_code: String,
    @Expose
    @SerializedName("weigth")
    val weight: Int
): Parcelable