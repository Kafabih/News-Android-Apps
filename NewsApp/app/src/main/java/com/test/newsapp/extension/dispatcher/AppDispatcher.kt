package com.test.newsapp.ui.extension.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Kafabih on 5/6/21.
 * Android Engineer
 */
class AppDispatcher : DispatcherProvider {
    override fun ui(): CoroutineDispatcher = Dispatchers.Main
    override fun main(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}