package com.svck.ilovemovie.data.model.response.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataReview(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Result>,
) : Parcelable {

    @Parcelize
    data class Result(
        @SerializedName("author") val author: String,
        @SerializedName("author_details") val author_details: AuthorDetails,
        @SerializedName("content") val content: String,
        @SerializedName("created_at") val created_at: String,
        @SerializedName("id") val id: String,
        @SerializedName("updated_at") val updated_at: String,
        @SerializedName("url") val url: String,
    ): Parcelable {

        @Parcelize
        data class AuthorDetails (
            @SerializedName("name") val name: String,
            @SerializedName("username") val username: String,
            @SerializedName("avatar_path") val avatar_path: String?,
            @SerializedName("rating") val rating: Double?,
        ) : Parcelable
    }

}