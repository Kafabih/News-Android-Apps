package com.svck.ilovemovie.presentation.homepage.view

import android.os.Bundle
import android.util.Log
import android.viewbinding.library.activity.viewBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.constants.BundleKeyConstant
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.databinding.FragmentAllMovieBinding
import com.svck.ilovemovie.domain.base.activity.BaseActivity
import com.svck.ilovemovie.external.extension.notNull
import com.svck.ilovemovie.external.extension.setVisibleIf
import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllMovieActivity : BaseActivity(), MovieAdapter.Listener {

    private val viewModel: HomepageViewModel by viewModel()
    private val binding: FragmentAllMovieBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData()
        observeLoadingState()
        observeNowPlaying()
        observeUpcoming()
        observePopular()
        observeTopRated()

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

    private fun observeNowPlaying() {
        viewModel.nowPlaying.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    setupListView(viewModel.currListMovie, AppConstant.SORT_BY.NOW_PLAYING)
                } else {
                    setupListView(mutableListOf(), AppConstant.SORT_BY.NOW_PLAYING)
                }
            }
        }
    }

    private fun observeUpcoming() {
        viewModel.upcoming.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    setupListView(viewModel.currListMovie, AppConstant.SORT_BY.UPCOMING)
                } else {
                    setupListView(mutableListOf(), AppConstant.SORT_BY.UPCOMING)
                }
            }
        }
    }

    private fun observePopular() {
        viewModel.popular.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    setupListView(viewModel.currListMovie, AppConstant.SORT_BY.POPULAR)
                } else {
                    setupListView(mutableListOf(), AppConstant.SORT_BY.POPULAR)
                }
            }
        }
    }

    private fun observeTopRated() {
        viewModel.topRated.observe(this) { data ->
            data.notNull {
                if (data != null) {
                    setupListView(viewModel.currListMovie, AppConstant.SORT_BY.TOP_RATED)
                } else {
                    setupListView(mutableListOf(), AppConstant.SORT_BY.TOP_RATED)
                }
            }
        }
    }

    private fun init(category: String) {
        Log.d("currPage", viewModel.curr_page.toString())
        when(category) {
            AppConstant.SORT_BY.NOW_PLAYING -> {
                viewModel.fetchNowPlayingMovies(viewModel.curr_page)
            }

            AppConstant.SORT_BY.UPCOMING -> {
                viewModel.fetchUpcomingMovies(viewModel.curr_page)
            }

            AppConstant.SORT_BY.POPULAR -> {
                viewModel.fetchPopularMovies(viewModel.curr_page)
            }

            AppConstant.SORT_BY.TOP_RATED -> {
                viewModel.fetchTopRatedMovies(viewModel.curr_page)

            }
        }
    }

    private fun setupListView(list: MutableList<DataMovie.Results>, category: String){
        val layoutManager = GridLayoutManager(this, 2)

        binding.rvMovie.setVisibleIf(list.isNotEmpty())

        val categoryAdapter: MovieAdapter =
            MovieAdapter(this, list, this)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.adapter = categoryAdapter

        var loading = true
        var pastItemsVisible: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.rvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            viewModel.curr_page = viewModel.curr_page + 1

                            init(category)
                            loading = true
                        }
                    }
                }
            }
        })
    }

    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.root.setVisibleIf(isShow)
    }

    private fun getIntentData(){
        this.intent.let {
            val category = it.getStringExtra(BundleKeyConstant.MOVIE_CATEGORY)

            if(category != null){
                init(category)
            }
        }
    }

    override fun onClick(items: DataMovie.Results) {
        router.goToDetailMovies(this, items.id)
    }


}