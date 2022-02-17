package com.bangik.packclese.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at: String,
    @Expose
    @SerializedName("detail_transaction")
    val detail_transaction: List<DetailTransaction>,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("payment_url")
    val payment_url: String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("total")
    val total: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("user_id")
    val user_id: Int
): Parcelable