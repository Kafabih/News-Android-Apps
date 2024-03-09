package com.test.newsapp.koin.component

import com.test.newsapp.koin.module.appModules
import com.test.newsapp.koin.module.networkModule
import com.test.newsapp.presentation.homepage.module.homepageModule
import org.koin.core.module.Module


val appComponents: List<Module> = listOf(

    /**
     * Base Module
     */
    appModules,
    networkModule,


    /**
     * Features
     */
    homepageModule
)