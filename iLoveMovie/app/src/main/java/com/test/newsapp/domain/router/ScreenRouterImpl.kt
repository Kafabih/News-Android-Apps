package com.test.newsapp.domain.router

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.test.newsapp.domain.callback.DialogCallback
import com.test.newsapp.presentation.homepage.view.DetailActivity
import com.test.newsapp.presentation.homepage.view.HomepageActivity


class ScreenRouterImpl : ScreenRouter {

    override fun getScreenIntent(context: Context, screen: ScreenRouter.ActivityScreen): Intent? {
        val klazz: Class<*>? = when (screen) {
            ScreenRouter.ActivityScreen.Homepage -> HomepageActivity::class.java
            ScreenRouter.ActivityScreen.DetailPage -> DetailActivity::class.java
            else -> null
        }

        return if (klazz == null) null else Intent(context, klazz)
    }

    override fun getDialogFragmentScreenLayout(screen: ScreenRouter.DialogScreen, callback: DialogCallback): DialogFragment? {
        return when (screen) {

            else -> null
        }
    }

}