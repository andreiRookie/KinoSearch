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
import com.andreirookie.kinosearch.di.FavoriteFilmsFragComponent
import com.andreirookie.kinosearch.di.FeedFragViewModelFactory
import com.andreirookie.kinosearch.di.appComponent
import com.andreirookie.kinosearch.fragments.film.FilmDetailsFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFilmsFragment : Fragment() {

    private var _binding: FeedFragPagerLayoutBinding? = null
    private val binding: FeedFragPagerLayoutBinding get() = _binding!!

    private var _adapter: FilmAdapter? = null
    private val adapter: FilmAdapter get() = _adapter!!

    @Inject
    lateinit var vmFactory: FeedFragViewModelFactory
    private val viewModel: FeedFragViewModel by viewModels { vmFactory }

    private var vmJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FavoriteFilmsFragComponent
            .getComponent(ActivityComponentHolder.getComponent(context.appComponent))
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _adapter = FilmAdapter( object : FilmCardInteractionListener {
            override fun onCardClick(id: Int) {
                viewModel.navigateToFilmDetailsFrag(id)
            }
        })
        _binding = FeedFragPagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vmJob = viewModel.eventsFlow.onEach {
            when (it) {
                is FeedFragViewModel.Event.NavigateToFilmFragDetails -> {
                    val frag = FilmDetailsFragment.getInstance(it.id)
                    parentFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.feed_fragment_container, frag)
                        .addToBackStack(FilmDetailsFragment.TAG)
                        .commit()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter

            swipeRefreshLayout.apply {
                setColorSchemeColors(view.context.getColor(R.color.blue_200))
                setOnRefreshListener {
                    viewModel.getFavFilms()
                    isRefreshing = false
                }
            }

            retryButton.setOnClickListener {
                viewModel.getFavFilms()
            }
        }

        viewModel.getFavFilms()

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
            is FeedFragState.PopularFilms -> {}
            is FeedFragState.FavoriteFilms -> {
                adapter.submitList(state.favFilms)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        vmJob?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }

    companion object {
        const val TAB_TAG = "Favorite"
    }
}