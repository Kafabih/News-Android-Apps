package com.test.newsapp.presentation.homepage.view

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.test.newsapp.data.constants.BundleKeyConstant
import com.test.newsapp.data.model.response.DataArticle
import com.test.newsapp.databinding.ActivityDetailNewsBinding
import com.test.newsapp.domain.base.activity.BaseActivity
import com.test.newsapp.external.extension.loadImageFromUrlWithLoading
import com.test.newsapp.external.extension.setVisibleIf
import com.test.newsapp.external.utility.DateHelper
import com.test.newsapp.presentation.homepage.viewmodel.HomepageViewModel


class DetailActivity : BaseActivity() {

    private val binding: ActivityDetailNewsBinding by viewBinding()
    private val viewModel: HomepageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
    }

    private fun setupView(data: DataArticle){
        binding.tvSource.text = data.source.name
        binding.tvTitle.text = data.title
        binding.tvContentNews.text = data.content
        binding.tvDate.text = DateHelper().convertDateToDate(data.publishedAt)

        if(data.urlToImage != null){
            binding.ivThumbnail.loadImageFromUrlWithLoading(
                data.urlToImage, binding.bannerLoading.root
            )
        }

        binding.buttonBacaLengkap.setOnClickListener {
            if(data.url != null){
                binding.web.loadUrl(data.url)
                binding.web.settings.javaScriptEnabled = true
                binding.web.webViewClient = WebViewClient()

                binding.web.setVisibleIf(data.url.isNotEmpty())
            } else {
                showMessage("Something went wrong when accessing the URL Articles!")
                finish()
            }

        }

        binding.buttonBack.setOnClickListener { finish() }
    }

    private fun getIntentData(){
        this.intent?.let {
            val data = it.getParcelableExtra<DataArticle>(BundleKeyConstant.DATA_ARTICLE)

            if(data != null){
                setupView(data)
            } else {
                showMessage("Something went wrong")
                finish()
            }
        }
    }
}