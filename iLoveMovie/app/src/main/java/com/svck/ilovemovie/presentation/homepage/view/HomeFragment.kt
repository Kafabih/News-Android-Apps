package com.svck.ilovemovie.presentation.homepage.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.view.size
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.constants.DateConstant
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.databinding.FragmentHomeBinding
import com.svck.ilovemovie.databinding.ItemMovieBinding
import com.svck.ilovemovie.domain.base.fragment.BaseFragment
import com.svck.ilovemovie.external.extension.loadImageFromUrlWithLoading
import com.svck.ilovemovie.external.extension.notNull
import com.svck.ilovemovie.external.extension.roundOffDecimal
import com.svck.ilovemovie.external.extension.setVisibleIf
import com.svck.ilovemovie.external.extension.setup
import com.svck.ilovemovie.external.utility.DateHelper
import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomepageViewModel by viewModel()
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currListMovie.clear()

        observeLoadingState()
        observeNowPlaying()
        observeUpcoming()
        observePopular()
        observeTopRated()
    }

    private fun observeLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
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

    override fun onResume() {
        super.onResume()

        if (viewModel.nowPlaying.value == null) {
            viewModel.fetchNowPlayingMovies(1)
        }

        if (viewModel.upcoming.value == null) {
            viewModel.fetchUpcomingMovies(1)
        }

        if (viewModel.popular.value == null) {
            viewModel.fetchPopularMovies(1)
        }

        if (viewModel.topRated.value == null) {
            viewModel.fetchTopRatedMovies(1)
        }
    }

    private fun observeNowPlaying() {
        viewModel.nowPlaying.observe(viewLifecycleOwner) { data ->
            data.notNull {
                if (data != null) {
                    setupNowPlaying(data.results)
                } else {
                    setupNowPlaying(listOf())
                }
            }
        }
    }

    private fun observeUpcoming() {
        viewModel.upcoming.observe(viewLifecycleOwner) { data ->
            data.notNull {
                if (data != null) {
                    setupUpComing(data.results)
                } else {
                    setupUpComing(listOf())
                }
            }
        }
    }

    private fun observePopular() {
        viewModel.popular.observe(viewLifecycleOwner) { data ->
            data.notNull {
                if (data != null) {
                    setupPopular(data.results)
                } else {
                    setupPopular(listOf())
                }
            }
        }
    }

    private fun observeTopRated() {
        viewModel.topRated.observe(viewLifecycleOwner) { data ->
            data.notNull {
                if (data != null) {
                    setupTopRated(data.results)
                } else {
                    setupTopRated(listOf())
                }
            }
        }
    }

    private fun setupNowPlaying(list: List<DataMovie.Results>) {

        binding.tvSeeAllNowPlaying.setOnClickListener {
            router.goToAllMovies(requireContext(), AppConstant.SORT_BY.NOW_PLAYING)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvNowPlaying.setup(
            list,
            ItemMovieBinding::inflate,
            { view, data ->
                if (list.isEmpty()) {
                    view?.ivMovie?.setImageResource(R.drawable.ic_noimage)
                } else {
                    view?.ivMovie?.loadImageFromUrlWithLoading(
                        data.poster_path, view.bannerLoading.root
                    )

                    view?.tvTitle?.text = data.title
                    view?.tvRating?.text = roundOffDecimal(data.vote_average).toString()
                    view?.tvYear?.text = DateHelper().fromDateTimeZone(
                        dateString = data.release_date,
                        format = DateConstant.yyyyMMddHHmmss
                    )

                }
            },
            {
                router.goToDetailMovies(requireContext(), it.id)
            },
            layoutManager
        )
    }

    private fun setupUpComing(list: List<DataMovie.Results>) {

        binding.tvSeeAllUpcoming.setOnClickListener {
            router.goToAllMovies(requireContext(), AppConstant.SORT_BY.UPCOMING)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvUpcoming.setup(
            list,
            ItemMovieBinding::inflate,
            { view, data ->
                if (list.isEmpty()) {
                    view?.ivMovie?.setImageResource(R.drawable.ic_noimage)
                } else {
                    view?.ivMovie?.loadImageFromUrlWithLoading(
                        data.poster_path, view.bannerLoading.root
                    )

                    view?.tvTitle?.text = data.title
                    view?.tvRating?.text = roundOffDecimal(data.vote_average).toString()
                    view?.tvYear?.text = DateHelper().fromTimeStamp(
                        dateString = data.release_date,
                        format = DateConstant.yyyy
                    )

                }
            },
            {
                router.goToDetailMovies(requireContext(), it.id)


            },
            layoutManager
        )
    }

    private fun setupPopular(list: List<DataMovie.Results>) {

        binding.tvSeeAllPopular.setOnClickListener {
            router.goToAllMovies(requireContext(), AppConstant.SORT_BY.POPULAR)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvPopular.setup(
            list,
            ItemMovieBinding::inflate,
            { view, data ->
                if (list.isEmpty()) {
                    view?.ivMovie?.setImageResource(R.drawable.ic_noimage)
                } else {
                    view?.ivMovie?.loadImageFromUrlWithLoading(
                        data.poster_path, view.bannerLoading.root
                    )

                    view?.tvTitle?.text = data.title

                    view?.tvRating?.text = roundOffDecimal(data.vote_average).toString()
                    view?.tvYear?.text = DateHelper().fromTimeStamp(
                        dateString = data.release_date,
                        format = DateConstant.yyyy
                    )

                }
            },
            {
                router.goToDetailMovies(requireContext(), it.id)

            },
            layoutManager
        )
    }

    private fun setupTopRated(list: List<DataMovie.Results>) {
        binding.tvSeeAllTopRated.setOnClickListener {
            router.goToAllMovies(requireContext(), AppConstant.SORT_BY.TOP_RATED)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvTopRated.setup(
            list,
            ItemMovieBinding::inflate,
            { view, data ->
                if (list.isEmpty()) {
                    view?.ivMovie?.setImageResource(R.drawable.ic_noimage)
                } else {
                    view?.ivMovie?.loadImageFromUrlWithLoading(
                        data.poster_path, view.bannerLoading.root
                    )

                    view?.tvTitle?.text = data.title
                    view?.tvRating?.text = roundOffDecimal(data.vote_average).toString()
                    view?.tvYear?.text = DateHelper().fromTimeStamp(
                        dateString = data.release_date,
                        format = DateConstant.yyyy
                    )

                }
            },
            {
                router.goToDetailMovies(requireContext(), it.id)
            },
            layoutManager
        )
    }


    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.root.setVisibleIf(isShow)
    }


}