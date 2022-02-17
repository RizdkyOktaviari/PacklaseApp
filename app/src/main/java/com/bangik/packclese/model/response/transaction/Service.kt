package com.bangik.packclese.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("deleted_at")
    val deleted_at: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("jenisservice_id")
    val jenisservice_id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("price")
    val price: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("jenis_services")
    val jenis_services: JenisServices
): Parcelable