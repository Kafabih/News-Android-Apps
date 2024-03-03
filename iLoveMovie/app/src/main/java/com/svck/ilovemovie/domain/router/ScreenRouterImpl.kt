package com.svck.ilovemovie.domain.router

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.svck.ilovemovie.domain.callback.DialogCallback
import com.svck.ilovemovie.presentation.homepage.view.AllMovieActivity
import com.svck.ilovemovie.presentation.homepage.view.DetailMovieActivity
import com.svck.ilovemovie.presentation.homepage.view.DialogPlayVideo
import com.svck.ilovemovie.presentation.homepage.view.HomepageActivity


class ScreenRouterImpl : ScreenRouter {

    override fun getScreenIntent(context: Context, screen: ScreenRouter.ActivityScreen): Intent? {
        val klazz: Class<*>? = when (screen) {
            ScreenRouter.ActivityScreen.Homepage -> HomepageActivity::class.java
            ScreenRouter.ActivityScreen.AllMovie -> AllMovieActivity::class.java
            ScreenRouter.ActivityScreen.DetailMovie -> DetailMovieActivity::class.java
            else -> null
        }

        return if (klazz == null) null else Intent(context, klazz)
    }

    override fun getDialogFragmentScreenLayout(screen: ScreenRouter.DialogScreen, callback: DialogCallback): DialogFragment? {
        return when (screen) {
            ScreenRouter.DialogScreen.VIDEO_DIALOG -> DialogPlayVideo(callback)
            else -> null
        }
    }

}