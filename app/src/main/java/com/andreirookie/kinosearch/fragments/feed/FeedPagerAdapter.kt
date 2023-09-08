package com.andreirookie.kinosearch.fragments.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FeedPagerAdapter(
    fragManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragManager, lifecycle) {

    private val _pagerFragments: MutableList<Fragment> = mutableListOf()
    override fun getItemCount(): Int = _pagerFragments.size
    override fun createFragment(position: Int): Fragment = _pagerFragments[position]
    fun updateFragments(fragments: List<Fragment>) {
        _pagerFragments.clear()
        _pagerFragments.addAll(fragments)
        notifyItemRangeInserted(0, fragments.size)
    }
}