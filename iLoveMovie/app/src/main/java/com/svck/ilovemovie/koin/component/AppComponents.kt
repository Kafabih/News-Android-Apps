package com.svck.ilovemovie.koin.component

import com.svck.ilovemovie.koin.module.appModules
import com.svck.ilovemovie.koin.module.networkModule
import com.svck.ilovemovie.presentation.homepage.module.homepageModule
import com.svck.ilovemovie.presentation.startpage.module.startPageModule
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
    startPageModule,
    homepageModule
)