package com.svck.ilovemovie.presentation.startpage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.svck.ilovemovie.data.constants.SecretKeyConstant
import com.svck.ilovemovie.data.model.response.auth.GuestSessionResponse
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.domain.base.viewmodel.BaseViewModel
import com.svck.ilovemovie.external.extension.asLiveData
import com.svck.ilovemovie.external.extension.notNull
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel: BaseViewModel() {

    private val _guestSession = MutableLiveData<GuestSessionResponse?>()

    val guestSession = _guestSession.asLiveData()

    //Create Guest Session
    fun createGuestSession(){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response = repository.createGuestSession("Bearer " + SecretKeyConstant.API_KEY).await()
                    getGuestData(response)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getGuestData(response: Response<GuestSessionResponse>){
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { auth ->
                    if (auth.success) {
                        appSession.saveDataGuest(auth)
                        _guestSession.postValue(auth)
                        Log.d("RESAUTH", auth.toString())
                        _loadingState.postValue(LoadingState.LOADED)
                    } else {
                        Log.d("mesAuth", auth.toString())
//                        _loadingState.postValue(LoadingState.error(auth.getErrorMessage()))
                    }
                }
            } else {

                Log.d("mesauth", response.message())

                checkErrorResponse(response)
            }
        }

    }
}