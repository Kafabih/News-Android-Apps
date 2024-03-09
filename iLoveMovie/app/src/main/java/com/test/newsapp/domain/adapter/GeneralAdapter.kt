package com.test.newsapp.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

class GeneralAdapter<T : ViewBinding, ITEM>(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    private val bindHolder: View.(T?, ITEM) -> Unit
) : AbstractAdapter<T, ITEM>(items, bindingClass) {

    private var itemClick: View.(ITEM) -> Unit = {}
    private var itemClickWithBinding: View.(T?,ITEM) -> Unit = {_,_ ->}
    var viewBinding: T? = null

    constructor(
        items: List<ITEM>,
        bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
        bindHolder: View.(T?, ITEM) -> Unit,
        itemViewClick: View.(ITEM) -> Unit = {}
    ) : this(items, bindingClass, bindHolder) {
        this.itemClick = itemViewClick
    }

    constructor(
        items: List<ITEM>,
        bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
        bindHolder: View.(T?, ITEM) -> Unit,
        itemViewClickWithBinding: View.(T?,ITEM) -> Unit,
    ) : this(items, bindingClass, bindHolder) {
        this.itemClickWithBinding = itemViewClickWithBinding
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == holder.adapterPosition) {
            this.viewBinding = binding
            holder.itemView.bindHolder(binding, itemList[position])
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemView.itemClick(itemList[position])
    }

    override fun onItemClickWithBinding(binding: T?, itemView: View, position: Int) {
        itemView.itemClickWithBinding(binding, itemList[position])
    }


}