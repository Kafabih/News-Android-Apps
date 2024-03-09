package com.test.newsapp.presentation.homepage.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.viewbinding.library.activity.viewBinding
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.test.newsapp.R
import com.test.newsapp.data.constants.AppConstant
import com.test.newsapp.data.model.DataCategory
import com.test.newsapp.data.model.response.DataArticle
import com.test.newsapp.data.state.LoadingState
import com.test.newsapp.databinding.ActivityHomeBinding
import com.test.newsapp.databinding.ItemCategoryBinding
import com.test.newsapp.databinding.ItemNewsBinding
import com.test.newsapp.domain.base.activity.BaseActivity
import com.test.newsapp.external.extension.loadImageFromUrlWithLoading
import com.test.newsapp.external.extension.notNull
import com.test.newsapp.external.extension.setVisibleIf
import com.test.newsapp.external.extension.setup
import com.test.newsapp.external.utility.DateHelper
import com.test.newsapp.presentation.homepage.viewmodel.HomepageViewModel


class HomepageActivity : BaseActivity() {

    private val binding: ActivityHomeBinding by viewBinding()
    private val viewModel: HomepageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoadingState()
        observeNews()

    }

    override fun onResume() {
        super.onResume()

        if(viewModel.dataArticles.value == null){
            viewModel.fetchNews(null)
        }
    }

    private fun observeLoadingState() {
        viewModel.loadingState.observe(this) {
            when (it.status) {
                LoadingState.Status.RUNNING -> showLoading(true)
                LoadingState.Status.SUCCESS -> showLoading(false)
                LoadingState.Status.FAILED -> {
                    showLoading(false)
                    Log.d("message", it.message.toString())
                    it.message.notNull { message ->
//                        showLongMessage(message)

                        when {
                            (message == AppConstant.CONNECTION_ISSUE) -> {
                                showLongMessage(getString(R.string.error_processing_your_request_please_check_your_connection_try_again))
                            }

                            else -> {
                                showLongMessage(message)
                            }
                        }
                    }
                }

                LoadingState.Status.UNAUTHORIZED -> {
                    showLoading(false)
                    showLongMessage(getString(R.string.error_processing_your_request_please_check_your_connection_try_again))
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.root.setVisibleIf(isShow)
    }

    private fun observeNews() {
        viewModel.dataArticles.observe(this) { data ->
            if (data?.isNotEmpty() == true) {
                setupView(data)
            } else {
                setupView(listOf())
            }
        }
    }

    private fun setupView(list: List<DataArticle>) {
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    if(v.text.isNotEmpty()) {
                        viewModel.fetchNews(v.text.toString())
                        viewModel.dataCategory.add(DataCategory(v.text.toString()))
                        showMessage("Fetching data...")
                        true
                    } else {
                        false
                    }
//                    showMessage(v.text.toString())

                }
                else -> false
            }

        }
        binding.rvNews.setup(
            list,
            ItemNewsBinding::inflate,
            { view, data ->
                if (list.isEmpty()) {
                    view?.ivThumbnail?.setImageResource(R.drawable.ic_noimage)
                } else {
                    view?.ivThumbnail?.loadImageFromUrlWithLoading(
                        data.urlToImage, view.bannerLoading.root
                    )

                    view?.tvTitleNews?.text = data.title
                    view?.tvSource?.text = data.source.name

                    view?.tvDate?.text = DateHelper().convertDateToDate(data.publishedAt)
                }

            },
            {
                router.goToDetailPage(this@HomepageActivity, it)
            }
        )

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.setup(
            viewModel.dataCategory,
            ItemCategoryBinding::inflate,
            { view, data ->

                view?.tvName?.text = data.category

            }, {
                viewModel.fetchNews(it.category)
            },
            layoutManager
        )
    }
}