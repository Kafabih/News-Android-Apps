package com.svck.ilovemovie.presentation.homepage.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svck.ilovemovie.R
import com.svck.ilovemovie.data.constants.DateConstant
import com.svck.ilovemovie.data.model.response.movies.DataMovie
import com.svck.ilovemovie.databinding.ItemMovieBinding
import com.svck.ilovemovie.external.extension.loadImageFromUrlWithLoading
import com.svck.ilovemovie.external.extension.roundOffDecimal
import com.svck.ilovemovie.external.utility.DateHelper

class MovieAdapter(
    private val clickListener: Listener,
    private val items: MutableList<DataMovie.Results>,
    private val context: Context
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface Listener {
        fun onClick(items: DataMovie.Results)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleItem: DataMovie.Results = items[position]
        holder.bind(singleItem, clickListener)
    }

    inner class ViewHolder(val binding: ItemMovieBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            listItem: DataMovie.Results,
            clickListener: Listener,
        ) {
            listItem.let {
                binding.ivMovie.loadImageFromUrlWithLoading(
                    it.poster_path, binding.bannerLoading.root
                )

                binding.tvTitle.text = it.title
                binding.tvRating.text = roundOffDecimal(it.vote_average).toString()
                binding.tvYear.text = DateHelper().fromTimeStamp(
                    dateString = it.release_date,
                    format = DateConstant.yyyy
                )

            }

            binding.cvMovie.setOnClickListener {
                clickListener.onClick(listItem)
                notifyDataSetChanged()
            }
        }
    }
}