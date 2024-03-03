package com.svck.ilovemovie.data.network.repository

import com.svck.ilovemovie.data.model.response.auth.GuestSessionResponse
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.model.response.movies.DataReview
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.data.model.response.movies.DetailMovie
import com.svck.ilovemovie.data.network.services.MovieServices
import com.svck.ilovemovie.domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response


class MovieDataStore(
    private val service: MovieServices,
    private val dispatcher: DispatcherProvider
) : MovieRepository {
    override suspend fun createGuestSession(authHeader: String): Deferred<Response<GuestSessionResponse>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.createGuestSession(authHeader).await() }
            }
        }
    }

    override suspend fun discoverMovies(
        authHeader: String,
        sort_by: String,
        page: Int,
        with_release_type: Int?
    ): Deferred<Response<DataMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.discoverMovies(authHeader, sort_by, page, with_release_type).await() }
            }
        }
    }

    override suspend fun getNowPlayingMovies(
        authHeader: String,
        page: Int
    ): Deferred<Response<DataMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getNowPlayingMovies(authHeader, page).await() }
            }
        }
    }

    override suspend fun getUpcomingMovies(
        authHeader: String,
        page: Int
    ): Deferred<Response<DataMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getUpcomingMovies(authHeader, page).await() }
            }
        }
    }

    override suspend fun getPopularMovies(
        authHeader: String,
        page: Int
    ): Deferred<Response<DataMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getPopularMovies(authHeader, page).await() }
            }
        }
    }

    override suspend fun getTopRatedMovies(
        authHeader: String,
        page: Int
    ): Deferred<Response<DataMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getTopRatedMovies(authHeader, page).await() }
            }
        }
    }

    override suspend fun getDetailMovie(
        authHeader: String,
        id: Int
    ): Deferred<Response<DetailMovie>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getDetailMovie(authHeader, id).await() }
            }
        }
    }

    override suspend fun getReviewMovie(
        authHeader: String,
        id: Int,
        page: Int
    ): Deferred<Response<DataReview>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getReviewMovie(authHeader, id, page).await() }
            }
        }
    }

    override suspend fun getDataVideo(
        authHeader: String,
        id: Int
    ): Deferred<Response<DataVideo>> {
        return withContext(dispatcher.io()) {
            coroutineScope {
                async { service.getDataVideo(authHeader, id).await() }
            }
        }
    }

}