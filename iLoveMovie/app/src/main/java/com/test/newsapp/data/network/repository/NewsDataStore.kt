package com.test.newsapp.data.network.repository

import com.test.newsapp.data.model.response.BaseResponse
import com.test.newsapp.data.model.response.DataArticle
import com.test.newsapp.data.network.services.NewsService
import com.test.newsapp.domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response


class NewsDataStore(
    private val service: NewsService,
    private val dispatcher: DispatcherProvider
) : NewsRepository {
    override suspend fun getNews(search_for: String, apiKey: String): Deferred<Response<BaseResponse<List<DataArticle>>>> {

        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getNews(search_for, apiKey).await() }
            }
        }
    }


}