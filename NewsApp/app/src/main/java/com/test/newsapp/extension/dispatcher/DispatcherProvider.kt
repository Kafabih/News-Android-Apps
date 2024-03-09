package com.test.newsapp.ui.extension.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Kafabih on 5/6/21.
 * Android Engineer
 */
interface DispatcherProvider {
    fun ui(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}