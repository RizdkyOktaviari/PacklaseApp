package com.bangik.packclese.model.response.home

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("created_at")
    val created_at: String?,
    @Expose
    @SerializedName("description")
    val description: String?,
    @Expose
    @SerializedName("id")
    val id: Int?,
    @Expose
    @SerializedName("jenis")
    val jenis: String?,
    @Expose
    @SerializedName("picturePath")
    val picturePath: String?,
    @Expose
    @SerializedName("slug")
    val slug: String?,
    @Expose
    @SerializedName("rate")
    val rate: Float,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String?
) : Parcelable