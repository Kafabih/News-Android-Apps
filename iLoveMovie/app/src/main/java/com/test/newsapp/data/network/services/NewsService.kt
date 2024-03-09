package com.test.newsapp.data.network.services

import com.test.newsapp.data.constants.RestConstant
import com.test.newsapp.data.model.response.BaseResponse
import com.test.newsapp.data.model.response.DataArticle
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


@JvmSuppressWildcards
interface NewsService {

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.NEWS_EVERYTHING)
    fun getNews(@Query("q") search_for: String,@Query("apiKey") api_key: String): Deferred<Response<BaseResponse<List<DataArticle>>>>

}