package com.svck.ilovemovie.koin.module

import android.location.Geocoder
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.gson.Gson
import com.svck.ilovemovie.domain.router.RouterNavigation
import com.svck.ilovemovie.domain.router.ScreenRouter
import com.svck.ilovemovie.domain.router.ScreenRouterImpl
import com.svck.ilovemovie.domain.session.AppSession
import com.svck.ilovemovie.external.utility.DateHelper
import com.svck.ilovemovie.external.utility.OSUtility

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
    single { ReviewManagerFactory.create(androidContext()) }
}