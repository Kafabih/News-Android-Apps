package com.svck.ilovemovie.data.network.repository

import com.svck.ilovemovie.data.model.response.auth.GuestSessionResponse
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.model.response.movies.DataReview
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.data.model.response.movies.DetailMovie
import kotlinx.coroutines.Deferred
import retrofit2.Response


interface MovieRepository {
    suspend fun createGuestSession(authHeader: String): Deferred<Response<GuestSessionResponse>>
    suspend fun discoverMovies(authHeader: String, sort_by: String, page: Int, with_release_type: Int?): Deferred<Response<DataMovie>>
    suspend fun getNowPlayingMovies(authHeader: String, page: Int): Deferred<Response<DataMovie>>
    suspend fun getUpcomingMovies(authHeader: String, page: Int): Deferred<Response<DataMovie>>
    suspend fun getPopularMovies(authHeader: String, page: Int): Deferred<Response<DataMovie>>
    suspend fun getTopRatedMovies(authHeader: String, page: Int): Deferred<Response<DataMovie>>
    suspend fun getDetailMovie(authHeader: String, id: Int): Deferred<Response<DetailMovie>>
    suspend fun getReviewMovie(authHeader: String, id: Int, page: Int): Deferred<Response<DataReview>>
    suspend fun getDataVideo(authHeader: String, id: Int): Deferred<Response<DataVideo>>
}