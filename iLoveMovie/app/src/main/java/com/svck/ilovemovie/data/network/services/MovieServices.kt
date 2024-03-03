package com.svck.ilovemovie.data.network.services

import com.svck.ilovemovie.data.constants.RestConstant
import com.svck.ilovemovie.data.model.response.auth.GuestSessionResponse
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.model.response.movies.DataReview
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.data.model.response.movies.DetailMovie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


@JvmSuppressWildcards
interface MovieServices {

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.NEW_GUEST_SESSION)
    fun createGuestSession(@Header("Authorization") authHeader: String): Deferred<Response<GuestSessionResponse>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.DISCOVER_MOVIE)
    fun discoverMovies(@Header("Authorization") authHeader: String, @Query("sort_by") sort_by: String, @Query("page") page: Int, @Query("with_release_type") with_release_type: Int?): Deferred<Response<DataMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.NOW_PLAYING)
    fun getNowPlayingMovies(@Header("Authorization") authHeader: String, @Query("page") page: Int): Deferred<Response<DataMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.UPCOMING)
    fun getUpcomingMovies(@Header("Authorization") authHeader: String, @Query("page") page: Int): Deferred<Response<DataMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.POPULAR)
    fun getPopularMovies(@Header("Authorization") authHeader: String, @Query("page") page: Int): Deferred<Response<DataMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.TOP_RATED)
    fun getTopRatedMovies(@Header("Authorization") authHeader: String, @Query("page") page: Int): Deferred<Response<DataMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.DETAIL_MOVIE)
    fun getDetailMovie(@Header("Authorization") authHeader: String, @Path("movie_id") movie_id: Int): Deferred<Response<DetailMovie>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.REVIEW_MOVIE)
    fun getReviewMovie(@Header("Authorization") authHeader: String, @Path("movie_id") movie_id: Int,  @Query("page") page: Int): Deferred<Response<DataReview>>

    @Headers(RestConstant.HEADERS.CONTENT_JSON)
    @GET(RestConstant.API_FUNCTION.VIDEO_TRAILER)
    fun getDataVideo(@Header("Authorization") authHeader: String, @Path("movie_id") movie_id: Int): Deferred<Response<DataVideo>>
}