package com.test.newsapp.domain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class GeneralViewPagerAdapter(
    activity: FragmentActivity,
    private val fragmentList: List<Fragment?>
) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int) = fragmentList[position] ?: Fragment()
    override fun getItemCount() = fragmentList.size
}