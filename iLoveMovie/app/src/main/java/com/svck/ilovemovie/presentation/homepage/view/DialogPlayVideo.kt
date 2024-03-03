package com.svck.ilovemovie.presentation.homepage.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.fragment.app.viewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.AppConstant
import com.svck.ilovemovie.data.constants.BundleKeyConstant
import com.svck.ilovemovie.data.model.response.movies.DataVideo
import com.svck.ilovemovie.databinding.DialogVideoBinding
import com.svck.ilovemovie.domain.base.dialogfragment.BaseDialogFragment
import com.svck.ilovemovie.domain.callback.DialogCallback
import com.svck.ilovemovie.external.extension.notNull
import com.svck.ilovemovie.external.extension.setBackgroundDialog
import com.svck.ilovemovie.presentation.homepage.viewmodel.HomepageViewModel


class DialogPlayVideo(private val callback: DialogCallback) : BaseDialogFragment() {

    private val binding: DialogVideoBinding by viewBinding()
    private val viewModel: HomepageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle90)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setBackgroundDialog()
        initArguments()
    }

    private fun initArguments() {
        val data = arguments
        data.notNull { bundle ->
            val dataVideo = bundle.getParcelable<DataVideo.Results>(BundleKeyConstant.DATA_VIDEO)

            if (dataVideo != null) {
                initView(dataVideo)
            }
        }
    }

    private fun initView(data: DataVideo.Results){
        val uri = Uri.parse(AppConstant.BASE_YOUTUBE_URL + data.key)
        Log.d("url", AppConstant.BASE_YOUTUBE_URL + data.key)

        val youTubePlayerView: YouTubePlayerView = view?.findViewById(R.id.vv_trailer)!!
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data.key
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}