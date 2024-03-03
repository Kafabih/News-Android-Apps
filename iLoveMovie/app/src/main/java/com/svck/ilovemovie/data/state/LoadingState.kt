package com.svck.ilovemovie.data.state

data class LoadingState (val status: Status, val message: String? = null, val code:String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        val UNAUTHORIZED = LoadingState(Status.UNAUTHORIZED)
        fun error(message: String?,code:String? = null) = LoadingState(Status.FAILED, message,code)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        UNAUTHORIZED
    }
}