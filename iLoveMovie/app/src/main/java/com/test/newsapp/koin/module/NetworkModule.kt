package com.test.newsapp.koin.module

import com.test.newsapp.data.network.httpclient.coroutinesRestClient
import com.test.newsapp.data.network.httpclient.coroutinesServices
import com.test.newsapp.data.network.httpclient.httpClient
import com.test.newsapp.data.network.repository.NewsDataStore
import com.test.newsapp.data.network.repository.NewsRepository
import com.test.newsapp.domain.dispatcher.AppDispatcher
import com.test.newsapp.domain.dispatcher.DispatcherProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val networkModule = module {
    single<DispatcherProvider> { AppDispatcher() }
    single<NewsRepository> { NewsDataStore(get(), get()) }
    single { httpClient(androidApplication()) }
    single { coroutinesRestClient(get()) }
    single { coroutinesServices(get()) }
}
