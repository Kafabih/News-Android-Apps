package com.test.newsapp.data.network.httpclient

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.newsapp.BuildConfig
import com.test.newsapp.data.network.services.NewsService
import com.test.newsapp.external.extension.debugMode
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun httpClient(mainApp: Application): OkHttpClient {
    val httpCacheDir = File(mainApp.cacheDir, "httpCache")
    val maxSize = (10 * 1024 * 1024).toLong()
    val cache = Cache(httpCacheDir, maxSize)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val chuckerCollector = ChuckerCollector(
        context = mainApp,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    val httpChuck = ChuckerInterceptor.Builder(context = mainApp)
        .collector(collector = chuckerCollector)
        .maxContentLength(250000L)
        .redactHeaders(headerNames = setOf("Authorization", "Bearer"))
        .build()

    return OkHttpClient.Builder().apply {
        cache(cache)
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)


        /**
         * Adding debug interceptor
         */
        debugMode {
            addInterceptor(loggingInterceptor)
            addInterceptor(httpChuck)
        }

        addInterceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (error: Exception) {
                error.printStackTrace()
                val offlineRequest = chain.request().newBuilder()
                    .header(
                        "Cache-Control", "public, only-if-cached," +
                                "max-stale=" + 60 * 60 * 24
                    ).build()
                chain.proceed(offlineRequest)
            }
        }
    }.build()
}

fun coroutinesRestClient(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    val gson = GsonBuilder().setLenient().disableHtmlEscaping().create()

    builder.apply {
        client(okHttpClient)
        baseUrl(BuildConfig.BASE_URL)
        addCallAdapterFactory(CoroutineCallAdapterFactory())
        addConverterFactory(GsonConverterFactory.create(gson))
    }

    return builder.build()
}

fun coroutinesServices(restAdapter: Retrofit): NewsService {
    return restAdapter.create(NewsService::class.java)
}