package com.svck.ilovemovie.data.constants

object RestConstant {
    object HEADERS {
        const val CONTENT_JSON = "Content-Type: application/json"
        const val AUTHORIZATION = "Authorization"
    }

    const val TMDB_API = "core/"

    object API_FUNCTION{

        const val NEW_GUEST_SESSION = "authentication/guest_session/new"
        const val DISCOVER_MOVIE = "discover/movie"
        const val NOW_PLAYING = "movie/now_playing"
        const val UPCOMING = "movie/upcoming"
        const val POPULAR = "movie/popular"
        const val TOP_RATED = "movie/top_rated"
        const val DETAIL_MOVIE = "movie/{movie_id}"
        const val REVIEW_MOVIE = "movie/{movie_id}/reviews"
        const val VIDEO_TRAILER = "movie/{movie_id}/videos"
    }
}