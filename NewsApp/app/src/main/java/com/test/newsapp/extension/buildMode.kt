package com.test.newsapp.ui.extension

import com.test.newsapp.BuildConfig

/**
 * Created by Kafabih on 18/04/22
 * Android Engineer
 */

inline fun debugMode(body: () -> Unit){
    if(BuildConfig.DEBUG){
        body()
    }
}