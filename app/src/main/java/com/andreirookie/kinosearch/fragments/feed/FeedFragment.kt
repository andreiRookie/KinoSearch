package com.andreirookie.kinosearch.fragments.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.andreirookie.kinosearch.databinding.FeedFragLayoutBinding
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.FeedFragComponent
import com.andreirookie.kinosearch.di.FeedFragViewModelFactory
import com.andreirookie.kinosearch.di.appComponent
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class FeedFragment : Fragment() {

    private var _binding: FeedFragLayoutBinding? = null
    private val binding: FeedFragLayoutBinding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: FeedPagerAdapter

    @Inject
    lateinit var vmFactory: FeedFragViewModelFactory
    private val viewModel: FeedFragViewModel by viewModels { vmFactory }

    // TODO onBackpressed
    override fun onAttach(context: Context) {
        super.onAttach(context)
        FeedFragComponent
            .getComponent(ActivityComponentHolder.getComponent(context.appComponent))
            .inject(this)
    }

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

            pagerAdapter.updateFragments(listOf(PopularFilmsFragment(), FavoriteFilmsFragment()))
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