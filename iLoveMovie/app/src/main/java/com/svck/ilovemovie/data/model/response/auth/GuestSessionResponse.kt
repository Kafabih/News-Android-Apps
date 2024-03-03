package com.svck.ilovemovie.data.model.response.auth

import com.google.gson.annotations.SerializedName

data class GuestSessionResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id") val guest_session_id: String,
    @SerializedName("expires_at") val expires_at: String
)