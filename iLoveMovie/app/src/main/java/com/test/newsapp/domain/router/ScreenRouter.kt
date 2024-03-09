package com.test.newsapp.domain.router

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.test.newsapp.domain.callback.DialogCallback


interface ScreenRouter {

    sealed class ActivityScreen {
        object Homepage : ActivityScreen()
        object DetailPage : ActivityScreen()
    }

    sealed class DialogScreen {
    }


    fun getScreenIntent(context: Context, screen: ActivityScreen): Intent?
    fun getDialogFragmentScreenLayout(
        screen: DialogScreen,
        callback: DialogCallback
    ): DialogFragment?

}