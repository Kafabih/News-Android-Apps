package com.test.newsapp.domain.base.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test.newsapp.domain.dispatcher.DispatcherProvider
import com.test.newsapp.domain.session.AppSession
import com.test.newsapp.data.constants.AppConstant
import com.test.newsapp.data.enums.ResponseStatus
import com.test.newsapp.data.network.repository.NewsRepository
import com.test.newsapp.data.state.LoadingState
import com.test.newsapp.external.extension.SingleLiveEvent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val gson: Gson by inject()
    val appSession: AppSession by inject()
    val appDispatcher: DispatcherProvider by inject()
    val repository: NewsRepository by inject()

    val _loadingState = SingleLiveEvent<LoadingState>()
    val loadingState: LiveData<LoadingState> get() = _loadingState

    fun <T> checkErrorResponse(response: Response<T>?) {
        viewModelScope.launch(appDispatcher.io()) {
            Log.d("rescode", response?.code().toString())
            try {
                when (response?.code()) {
                    ResponseStatus.UNAUTHORIZED.code -> _loadingState.postValue(LoadingState.UNAUTHORIZED)
                    else -> _loadingState.postValue(LoadingState.error(AppConstant.CONNECTION_ISSUE))
                }
            } catch (error: Exception) {
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }
}