package com.svck.ilovemovie.presentation.startpage.module

import com.svck.ilovemovie.presentation.startpage.viewmodel.StartPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val startPageModule = module {
    viewModel { StartPageViewModel() }
}