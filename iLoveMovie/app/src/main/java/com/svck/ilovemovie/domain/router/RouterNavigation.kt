package com.svck.ilovemovie.domain.router

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.svck.ilovemovie.data.constants.BundleKeyConstant
import com.svck.ilovemovie.data.constants.DialogConstant
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.domain.callback.DialogCallback


class RouterNavigation(private val screenRouter: ScreenRouter) {
    fun goToMainpage(context: Context) {
        val screen = screenRouter.getScreenIntent(context, ScreenRouter.ActivityScreen.Homepage)
        screen?.run {
            context.startActivity(this)
        }
    }

    fun goToAllMovies(context: Context, category: String) {
        val screen = screenRouter.getScreenIntent(context, ScreenRouter.ActivityScreen.AllMovie)
        screen?.putExtra(BundleKeyConstant.MOVIE_CATEGORY, category)
        screen?.run {
            context.startActivity(this)
        }
    }

    fun goToDetailMovies(context: Context, movie_id: Int) {
        val screen = screenRouter.getScreenIntent(context, ScreenRouter.ActivityScreen.DetailMovie)
        screen?.putExtra(BundleKeyConstant.MOVIE_ID, movie_id)
        screen?.run {
            context.startActivity(this)
        }
    }

    //DIALOG FRAGMENT
    fun openVideoDialog(fragmentManager: FragmentManager, data: DataVideo.Results, callback: DialogCallback) {
        val screen =
            screenRouter.getDialogFragmentScreenLayout(ScreenRouter.DialogScreen.VIDEO_DIALOG, callback)
        val arguments = Bundle()
        arguments.putParcelable(BundleKeyConstant.DATA_VIDEO, data)
        screen?.arguments = arguments
        screen?.show(fragmentManager, DialogConstant.SCREEN.PLAY_VIDEO_DIALOG)
    }
}