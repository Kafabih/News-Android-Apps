package com.svck.ilovemovie.presentation.homepage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.constants.SecretKeyConstant
import com.svck.ilovemovie.data.model.response.auth.GuestSessionResponse
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.model.response.movies.DataReview
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.data.model.response.movies.DetailMovie
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.domain.base.viewmodel.BaseViewModel
import com.svck.ilovemovie.external.extension.asLiveData
import com.svck.ilovemovie.external.extension.notNull
import kotlinx.coroutines.launch
import retrofit2.Response

class HomepageViewModel : BaseViewModel() {

    var curr_page: Int = 1
    var id_movies: Int = 0

    private val _nowPlaying = MutableLiveData<DataMovie?>()
    private val _upcoming = MutableLiveData<DataMovie?>()
    private val _popular = MutableLiveData<DataMovie?>()
    private val _topRated = MutableLiveData<DataMovie?>()
    private val _detailMovie = MutableLiveData<DetailMovie?>()
    private val _dataReview = MutableLiveData<DataReview?>()
    private val _dataVideo = MutableLiveData<DataVideo?>()

    var currListMovie: MutableList<DataMovie.Results> = mutableListOf()
    var currListReview: MutableList<DataReview.Result> = mutableListOf()

    val nowPlaying = _nowPlaying.asLiveData()
    val upcoming = _upcoming.asLiveData()
    val popular = _popular.asLiveData()
    val topRated = _topRated.asLiveData()
    val detailMovie = _detailMovie.asLiveData()
    val dataReview = _dataReview.asLiveData()
    val dataVideo = _dataVideo.asLiveData()

    fun fetchNowPlayingMovies(page: Int){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response = repository.getNowPlayingMovies("Bearer " + SecretKeyConstant.API_KEY, page).await()
                    getNowPlaying(response)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getNowPlaying(response: Response<DataMovie>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _nowPlaying.postValue(data)
                    currListMovie.addAll(data.results)
                    _loadingState.postValue(LoadingState.LOADED)

                }
            } else {

                Log.d("mesauth", response.message())

                checkErrorResponse(response)
            }
        }
    }

    fun fetchUpcomingMovies(page: Int){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response = repository.getUpcomingMovies("Bearer " + SecretKeyConstant.API_KEY, page).await()
                    getUpcoming(response)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getUpcoming(response: Response<DataMovie>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _upcoming.postValue(data)
                    currListMovie.addAll(data.results)
                    _loadingState.postValue(LoadingState.LOADED)

                }
            } else {

                Log.d("errorUpcoming", response.message())

                checkErrorResponse(response)
            }
        }
    }

    fun fetchPopularMovies(page: Int){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response = repository.getPopularMovies("Bearer " + SecretKeyConstant.API_KEY, page).await()
                    getPopular(response)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getPopular(response: Response<DataMovie>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _popular.postValue(data)
                    currListMovie.addAll(data.results)
                    _loadingState.postValue(LoadingState.LOADED)

                }
            } else {

                Log.d("errorPopular", response.message())

                checkErrorResponse(response)
            }
        }

    }

    fun fetchTopRatedMovies(page: Int){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)


                this.launch {
                    val response = repository.getTopRatedMovies("Bearer " + SecretKeyConstant.API_KEY, page).await()
                    getTopRated(response)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getTopRated(response: Response<DataMovie>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _topRated.postValue(data)
                    currListMovie.addAll(data.results)
                    _loadingState.postValue(LoadingState.LOADED)

                    Log.d("topRated", data.toString())

                }
            } else {

                checkErrorResponse(response)
            }
        }
    }

    fun fetchDetailMovie(movie_id: Int){
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)

                this.launch {
                    val response = repository.getDetailMovie("Bearer " + SecretKeyConstant.API_KEY, movie_id).await()
                    getDetailMovie(response)
                }

            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getDetailMovie(response: Response<DetailMovie>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _detailMovie.postValue(data)
                    _loadingState.postValue(LoadingState.LOADED)

                    Log.d("detailMovie", data.toString())

                }
            } else {

                checkErrorResponse(response)
            }
        }
    }

    fun fetchDataReview(movie_id: Int, page: Int) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)

                this.launch {
                    val response = repository.getReviewMovie("Bearer " + SecretKeyConstant.API_KEY, movie_id, page).await()
                    getDataReview(response)
                }

            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getDataReview(response: Response<DataReview>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _dataReview.postValue(data)
                    currListReview.addAll(data.results)
                    _loadingState.postValue(LoadingState.LOADED)

                    Log.d("detailMovie", data.toString())

                }
            } else {

                checkErrorResponse(response)
            }
        }
    }

    fun fetchVideo(movie_id: Int) {
        viewModelScope.launch(appDispatcher.io()) {
            try {
                _loadingState.postValue(LoadingState.LOADING)

                this.launch {
                    val response = repository.getDataVideo("Bearer " + SecretKeyConstant.API_KEY, movie_id).await()
                    getDataVideo(response)
                }

            } catch (error: Exception) {
                error.printStackTrace()
                _loadingState.postValue(LoadingState.error(error.message))
            }
        }
    }

    private fun getDataVideo(response: Response<DataVideo>) {
        viewModelScope.launch {
            if (response.isSuccessful) {
                response.body()?.notNull { data ->
                    _dataVideo.postValue(data)
                    _loadingState.postValue(LoadingState.LOADED)

                }
            } else {

                checkErrorResponse(response)
            }
        }
    }
}