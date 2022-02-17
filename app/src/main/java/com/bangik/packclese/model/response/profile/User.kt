package com.bangik.packclese.model.response.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
        @Expose
        @SerializedName("address")
        val address: String,
        @Expose
        @SerializedName("created_at")
        val created_at: String,
        @Expose
        @SerializedName("google_id")
        val google_id: Any,
        @Expose
        @SerializedName("email")
        val email: String,
        @Expose
        @SerializedName("email_verified_at")
        val email_verified_at: String,
        @Expose
        @SerializedName("id")
        val id: Int,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @Expose
        @SerializedName("profile_photo_path")
        val profile_photo_path: String,
        @Expose
        @SerializedName("roles")
        val roles: String,
        @Expose
        @SerializedName("updated_at")
        val updated_at: String
)