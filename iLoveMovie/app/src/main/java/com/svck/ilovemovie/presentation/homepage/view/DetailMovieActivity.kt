package com.svck.ilovemovie.presentation.homepage.view

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.viewbinding.library.activity.viewBinding
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.constants.BundleKeyConstant
import com.svck.ilovemovie.data.constants.DateConstant
import com.svck.ilovemovie.data.model.response.movies.DataReview
import com.svck.ilovemovie.data.model.response.movies.DetailMovie
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.databinding.ActivityDetailMovieBinding
import com.svck.ilovemovie.databinding.ItemGenreBinding
import com.svck.ilovemovie.databinding.ItemReviewBinding
import com.svck.ilovemovie.domain.base.activity.BaseActivity
import com.svck.ilovemovie.domain.callback.DialogCallback
import com.svck.ilovemovie.external.extension.loadImageFromUrlWithLoading
import com.svck.ilovemovie.external.extension.notNull
import com.svck.ilovemovie.external.extension.roundOffDecimal
import com.svck.ilovemovie.external.extension.setVisibleIf
import com.svck.ilovemovie.external.extension.setup
import com.svck.ilovemovie.external.utility.DateHelper
import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity(), DialogCallback {

    private val viewModel: HomepageViewModel by viewModel()
    private val binding: ActivityDetailMovieBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.curr_page = 1
        getIntentData()
        observeLoadingState()
        observeDetailMovie()
        observeDataReview()
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

    private fun observeDetailMovie() {
        viewModel.detailMovie.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    initDetail(data)
                }
            }
        }
    }

    private fun observeDataReview() {
        viewModel.dataReview.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    setupReview(viewModel.currListReview)
                }
            }
        }
    }

    private fun observeDataVideo() {
        viewModel.dataVideo.observe(this) { data ->
            data.notNull {
                if(data != null){
                    Log.d("dataResultVideo", data.results.toString())
                    val dataVideo = data.results.find { it.type == AppConstant.TYPE_VIDEO.TRAILER && it.site == "YouTube" }
                    if (dataVideo != null) {
                        Log.d("YoutubeUrl", AppConstant.BASE_YOUTUBE_URL + dataVideo.key)

                        router.openVideoDialog(fragmentManager = supportFragmentManager, dataVideo, this)
                    } else {
                        showMessage("Video not found")
                    }
                }
            }
        }
    }

    private fun initDetail(data: DetailMovie) {
        if (data.backdrop_path != null) {
            binding.ivPoster.loadImageFromUrlWithLoading(
                data.backdrop_path, binding.bannerLoading.root
            )
        }

        if (data.poster_path != null) {
            binding.ivMovie.loadImageFromUrlWithLoading(
                data.poster_path, binding.bannerLoadingMovie.root
            )
        }

        binding.tvTitle.text = data.title
        binding.tvDesc.text = data.overview
        binding.tvRating.text = "${roundOffDecimal(data.vote_average)} ( ${data.vote_count} )"

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.isAutoMeasureEnabled = true

        binding.rvGenre.setup(
            data.genres,
            ItemGenreBinding::inflate,
            { view, item ->
                view?.tvGenre?.text = item.name
            },
            {

            },
            layoutManager
        )

        binding.ibTrailer.setOnClickListener {
            observeDataVideo()
        }
    }

    private fun setupReview(list: MutableList<DataReview.Result>) {
        val layoutManager = LinearLayoutManager(this,)

        binding.rvReview.setup(
            list,
            ItemReviewBinding::inflate,
            { view, item ->
                view?.tvName?.text = item.author
                if (item.author_details.avatar_path != null) {
                    view?.ivUser?.loadImageFromUrlWithLoading(
                        item.author_details.avatar_path, view.bannerLoading.root
                    )
                }
                if (item.author_details.rating != null) {
                    view?.tvRating?.text = roundOffDecimal(item.author_details.rating).toString()
                }

                view?.tvDesc?.text = item.content

                DateHelper().fromTimeStamp(
                    dateString = item.created_at,
                    format = DateConstant.yyyyMMddHHmmss
                )
                view?.tvDate?.text = item.created_at
            },
            {

            }
        )

        var loading = true
        var pastItemsVisible: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down

                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    pastItemsVisible = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastItemsVisible >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item !")

                            // Do pagination.. i.e. fetch new data
                            if(viewModel.curr_page <= viewModel.dataReview.value?.page!!){
                                viewModel.curr_page = viewModel.curr_page + 1

                                viewModel.fetchDataReview(viewModel.id_movies, viewModel.curr_page )
                                loading = true
                            }

                        }
                    }
                }
            }
        })
    }

    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.root.setVisibleIf(isShow)
    }

    private fun getIntentData() {
        this.intent.let {
            val id = it.getIntExtra(BundleKeyConstant.MOVIE_ID, 0)

            if (id != 0) {
                viewModel.id_movies = id
                viewModel.fetchDetailMovie(id)
                viewModel.fetchDataReview(id, viewModel.curr_page)
                viewModel.fetchVideo(id)

            }


        }
    }

    override fun onDialogCallback(data: Any) {

    }

}