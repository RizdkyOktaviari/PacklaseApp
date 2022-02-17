package com.bangik.packclese.model.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UploadPhotoResponse(
    @Expose
    @SerializedName("file")
    val file:String,
    @Expose
    @SerializedName("user")
    val user: User
)
