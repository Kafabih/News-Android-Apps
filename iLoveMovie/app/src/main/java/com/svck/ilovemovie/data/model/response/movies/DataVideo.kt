package com.svck.ilovemovie.data.model.response.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataVideo (
    @SerializedName("id") val id: String,
    @SerializedName("results") val results: List<Results>,
) : Parcelable {

    @Parcelize
    data class Results(
        @SerializedName("iso_639_1") val iso_639_1: String,
        @SerializedName("iso_3166_1") val iso_3166_1: String,
        @SerializedName("name") val name: String,
        @SerializedName("key") val key: String,
        @SerializedName("site") val site: String,
        @SerializedName("size") val size: Double,
        @SerializedName("type") val type: String,
        @SerializedName("official") val official: Boolean,
        @SerializedName("published_at") val published_at: String,
        @SerializedName("id") val id: String,
    ): Parcelable
}