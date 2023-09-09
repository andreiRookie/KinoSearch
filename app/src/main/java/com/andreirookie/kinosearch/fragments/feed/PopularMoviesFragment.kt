package com.andreirookie.kinosearch.fragments.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andreirookie.kinosearch.R
import com.andreirookie.kinosearch.databinding.FeedFragPagerLayoutBinding
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.FeedFragViewModelFactory
import com.andreirookie.kinosearch.di.PopularMoviesFragComponent
import com.andreirookie.kinosearch.di.appComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesFragment : Fragment() {

    private var _binding: FeedFragPagerLayoutBinding? = null
    private val binding: FeedFragPagerLayoutBinding get() = _binding!!

    private var _adapter: MovieAdapter? = null
    private val adapter: MovieAdapter get() = _adapter!!

    @Inject
    lateinit var vmFactory: FeedFragViewModelFactory
    private val viewModel: FeedFragViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        PopularMoviesFragComponent
            .getComponent(ActivityComponentHolder.getComponent(context.appComponent))
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _adapter = MovieAdapter()
        _binding = FeedFragPagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter


            swipeRefreshLayout.apply {
                setColorSchemeColors(view.context.getColor(R.color.blue_200))
                setOnRefreshListener {

                    isRefreshing = false
                }
            }
        }

        viewModel.getPopMovies()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.feedState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: FeedFragState) {
        when (state) {
            is FeedFragState.Init -> {}
            is FeedFragState.Error -> {}
            is FeedFragState.Loading -> {}
            is FeedFragState.TopMovies -> {
                adapter.submitList(state.popMovies)
            }

            is FeedFragState.FavoriteMovies -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _adapter = null
    }
}