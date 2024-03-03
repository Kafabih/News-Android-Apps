package com.svck.ilovemovie.presentation.homepage.module

import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel
import com.svck.ilovemovie.presentation.startpage.viewmodel.StartPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homepageModule = module {
    viewModel { HomepageViewModel() }
}