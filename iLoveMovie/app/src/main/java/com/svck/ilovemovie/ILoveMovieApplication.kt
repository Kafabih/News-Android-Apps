package com.svck.ilovemovie

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.gu.toolargetool.TooLargeTool
import com.svck.ilovemovie.external.extension.debugMode
import com.svck.ilovemovie.koin.component.appComponents
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ILoveMovieApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializeKoin()

        debugMode {
            TooLargeTool.startLogging(this)
        }

        //Disable darkmode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initializeKoin() {
        startKoin {
            debugMode { androidLogger(Level.ERROR) }
            androidContext(this@ILoveMovieApplication)
            modules(appComponents)
        }
    }
}