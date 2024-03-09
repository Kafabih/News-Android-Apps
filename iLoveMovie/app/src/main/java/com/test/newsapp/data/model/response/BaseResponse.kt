package com.test.newsapp.data.model.response

data class BaseResponse<T>(
    val status: String,
    val totalResults : Int,
    val articles: T?,
    val data: T?
)