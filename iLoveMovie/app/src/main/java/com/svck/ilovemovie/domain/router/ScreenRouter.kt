package com.svck.ilovemovie.domain.router

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.svck.ilovemovie.domain.callback.DialogCallback


interface ScreenRouter {

    sealed class ActivityScreen {
        object Homepage : ActivityScreen()
        object AllMovie : ActivityScreen()
        object DetailMovie : ActivityScreen()
    }

    sealed class DialogScreen {
        object VIDEO_DIALOG : DialogScreen()
    }


    fun getScreenIntent(context: Context, screen: ActivityScreen): Intent?
    fun getDialogFragmentScreenLayout(
        screen: DialogScreen,
        callback: DialogCallback
    ): DialogFragment?

}