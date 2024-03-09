package com.test.newsapp.presentation.homepage.module

import com.test.newsapp.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homepageModule = module {
    viewModel { HomepageViewModel() }
}