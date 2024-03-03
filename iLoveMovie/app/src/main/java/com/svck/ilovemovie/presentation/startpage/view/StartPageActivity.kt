package com.svck.ilovemovie.presentation.startpage.view

import android.os.Bundle
import android.util.Log
import android.viewbinding.library.activity.viewBinding
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.state.LoadingState
import com.svck.ilovemovie.databinding.ActivityGettingStartedBinding
import com.svck.ilovemovie.domain.base.activity.BaseActivity
import com.svck.ilovemovie.external.extension.notNull
import com.svck.ilovemovie.external.extension.setVisibleIf
import com.svck.ilovemovie.presentation.startpage.viewmodel.StartPageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartPageActivity : BaseActivity(){

    private val binding: ActivityGettingStartedBinding by viewBinding()
    private val viewModel: StartPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeLoadingState()
        observeGuestSession()
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

    private fun observeGuestSession() {
        viewModel.guestSession.observe(this) { auth ->
            auth.notNull {
                router.goToMainpage(this)
                finish()
            }
        }
    }


    private fun initView(){
        binding.btnStart.setOnClickListener {
            viewModel.createGuestSession()
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.layoutLoading.root.setVisibleIf(isShow)
    }
}