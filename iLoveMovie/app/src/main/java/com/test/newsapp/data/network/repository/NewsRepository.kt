package com.test.newsapp.data.network.repository

import com.test.newsapp.data.model.response.BaseResponse
import com.test.newsapp.data.model.response.DataArticle
import kotlinx.coroutines.Deferred
import retrofit2.Response


interface NewsRepository {
    suspend fun getNews(search_for: String, apiKey: String): Deferred<Response<BaseResponse<List<DataArticle>>>>

}