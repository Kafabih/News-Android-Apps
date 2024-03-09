package com.test.newsapp.koin.module

import android.location.Geocoder
import com.google.gson.Gson
import com.test.newsapp.domain.router.RouterNavigation
import com.test.newsapp.domain.router.ScreenRouter
import com.test.newsapp.domain.router.ScreenRouterImpl
import com.test.newsapp.domain.session.AppSession
import com.test.newsapp.external.utility.DateHelper
import com.test.newsapp.external.utility.OSUtility

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.*


val appModules = module {
    single { Gson() }
    single { OSUtility() }
    single { DateHelper() }
    single { AppSession(androidApplication()) }
    single<ScreenRouter> { ScreenRouterImpl() }
    single { RouterNavigation(get()) }
    single { Geocoder(androidContext(), Locale.getDefault()) }
}