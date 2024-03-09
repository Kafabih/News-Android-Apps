package com.test.newsapp.ui.repository


import com.test.newsapp.ui.data.api.NewsApi
import com.test.newsapp.ui.extension.dispatcher.DispatcherProvider

class NewsDataStore(
    private val service: NewsApi, private val dispatcher: DispatcherProvider
) : NewsRepository {



}