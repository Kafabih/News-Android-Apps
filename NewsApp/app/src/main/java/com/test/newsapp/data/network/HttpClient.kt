package com.test.newsapp.ui.data.network

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.newsapp.BuildConfig
import com.test.newsapp.ui.extension.debugMode
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Kafabih on 5/6/21.
 * Android Engineer
 */
fun getRetrofitClient(mainApp: Application): OkHttpClient {
    val httpCacheDir = File(mainApp.cacheDir, "httpCache")
    val maxSize = (10 * 1024 * 1024).toLong()
    val cache = Cache(httpCacheDir, maxSize)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder().apply {
        cache(cache)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)

    }.addInterceptor { chain ->
        chain.proceed(chain.request().newBuilder().also {
            it.addHeader("Accept", "application/json")
        }.build())
    }.also { client ->
        debugMode {
            client.addInterceptor(loggingInterceptor)
            client.addInterceptor(
                ChuckerInterceptor.Builder(mainApp).collector(ChuckerCollector(mainApp))
                    .maxContentLength(250000L).redactHeaders(emptySet())
                    .alwaysReadResponseBody(false).build()
            )
        }
    }.build()
}

fun <Api> buildApi(
    api: Class<Api>,
    app:Application,
): Api {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(getRetrofitClient(app))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(api)
}


