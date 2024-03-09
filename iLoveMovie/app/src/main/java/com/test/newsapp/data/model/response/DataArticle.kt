package com.test.newsapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataArticle(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
) :Parcelable {

    @Parcelize
    data class Source(
        val id: String?,
        val name: String
    ) :Parcelable
}