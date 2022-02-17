package com.bangik.packclese.model.response.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileEditResponse(
        @Expose
        @SerializedName("token_type")
        val token_type: String,
        @Expose
        @SerializedName("user")
        val user: User


)