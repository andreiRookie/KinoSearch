package com.andreirookie.kinosearch.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.andreirookie.kinosearch.databinding.FeedFragLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : Fragment() {

    private var _binding: FeedFragLayoutBinding? = null
    private val binding: FeedFragLayoutBinding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: FeedPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tabLayout = feedTabLayout
            viewPager = pager
            pagerAdapter = FeedPagerAdapter(childFragmentManager, lifecycle)

            pagerAdapter.updateFragments(listOf(PopularMoviesFragment(), FavoriteMoviesFragment()))
            viewPager.adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "FeedFragment"
        private val tabs = listOf("Popular", "Favorite")
    }
}