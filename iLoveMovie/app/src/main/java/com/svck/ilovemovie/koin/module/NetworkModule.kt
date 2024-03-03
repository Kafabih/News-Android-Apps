package com.svck.ilovemovie.koin.module

import com.svck.ilovemovie.data.network.httpclient.coroutinesRestClient
import com.svck.ilovemovie.data.network.httpclient.coroutinesServices
import com.svck.ilovemovie.data.network.httpclient.httpClient
import com.svck.ilovemovie.data.network.repository.MovieDataStore
import com.svck.ilovemovie.data.network.repository.MovieRepository
import com.svck.ilovemovie.domain.dispatcher.AppDispatcher
import com.svck.ilovemovie.domain.dispatcher.DispatcherProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val networkModule = module {
    single<DispatcherProvider> { AppDispatcher() }
    single<MovieRepository> { MovieDataStore(get(), get()) }
    single { httpClient(androidApplication()) }
    single { coroutinesRestClient(get()) }
    single { coroutinesServices(get()) }
}
