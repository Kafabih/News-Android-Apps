package com.test.newsapp.ui.di

import com.google.gson.Gson
import com.test.newsapp.ui.data.api.NewsApi
import com.test.newsapp.ui.data.network.buildApi
import com.test.newsapp.ui.domain.AppSession
import com.test.newsapp.ui.extension.dispatcher.AppDispatcher
import com.test.newsapp.ui.extension.dispatcher.DispatcherProvider
import com.test.newsapp.ui.repository.NewsDataStore
import com.test.newsapp.ui.repository.NewsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Kafabih on 11/03/22
 * Android Engineer
 */
val appModule = module {

    single { Gson() }
    single { AppSession(androidApplication()) }
    single { buildApi(NewsApi::class.java, androidApplication()) }
    single<DispatcherProvider> { AppDispatcher() }
    single<NewsRepository> { NewsDataStore(get(), get()) }

}