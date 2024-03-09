package com.test.newsapp.domain.router

import android.content.Context
import com.test.newsapp.data.constants.BundleKeyConstant
import com.test.newsapp.data.model.response.DataArticle


class RouterNavigation(private val screenRouter: ScreenRouter) {
    fun goToMainpage(context: Context) {
        val screen = screenRouter.getScreenIntent(context, ScreenRouter.ActivityScreen.Homepage)
        screen?.run {
            context.startActivity(this)
        }
    }

    fun goToDetailPage(context: Context, data: DataArticle) {
        val screen = screenRouter.getScreenIntent(context, ScreenRouter.ActivityScreen.DetailPage)
        screen?.putExtra(BundleKeyConstant.DATA_ARTICLE, data)
        screen?.run {
            context.startActivity(this)
        }
    }
}