package com.svck.ilovemovie.domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatcher : DispatcherProvider {
    override fun ui(): CoroutineDispatcher = Dispatchers.Main
    override fun main(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}