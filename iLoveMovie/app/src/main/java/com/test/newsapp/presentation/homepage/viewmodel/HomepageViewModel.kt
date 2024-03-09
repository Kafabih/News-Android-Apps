package com.test.newsapp.presentation.homepage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.newsapp.data.constants.AppConstant
import com.test.newsapp.data.constants.SecretKeyConstant
import com.test.newsapp.data.model.DataCategory
import com.test.newsapp.data.model.response.BaseResponse
import com.test.newsapp.data.model.response.DataArticle
import com.test.newsapp.data.state.LoadingState
import com.test.newsapp.domain.base.viewmodel.BaseViewModel
import com.test.newsapp.external.extension.asLiveData
import com.test.newsapp.external.extension.notNull
import kotlinx.coroutines.launch
import retrofit2.Response

class HomepageViewModel : BaseViewModel() {

    private val _dataArticles = MutableLiveData<List<DataArticle>?>()

    var dataCategory: MutableList<DataCategory> = mutableListOf(
        DataCategory("Football"),
        DataCategory("Sport"),
        DataCategory("E-Sport"),
        DataCategory("Science"),
        DataCategory("Politics"),
        DataCategory("History"),
    )

    val dataArticles = _dataArticles.asLiveData()

    // GET NEWS
    fun fetchNews(searchFor: String?){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response =
                        repository.getNews(searchFor ?: "Football" ,SecretKeyConstant.API_KEY).await()
                    getArticles(response)
                }

            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getArticles(response: Response<BaseResponse<List<DataArticle>>>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    if(data.status.equals("ok")){
                        if(data.articles != null){
                            _dataArticles.postValue(data.articles)
                            _loadingState.postValue(LoadingState.LOADED)
                        } else {
                            _loadingState.postValue(LoadingState.error("There is an issue when fetching data"))
                        }
                    } else {
                        _loadingState.postValue(LoadingState.error("There is an issue when fetching data"))
                    }


                }
            } else {

                Log.d("errorFetching", response.message())

                checkErrorResponse(response)
            }
        }
    }



}