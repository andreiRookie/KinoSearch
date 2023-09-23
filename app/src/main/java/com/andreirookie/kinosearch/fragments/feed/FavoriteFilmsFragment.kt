package com.andreirookie.kinosearch.fragments.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.andreirookie.kinosearch.R
import com.andreirookie.kinosearch.databinding.FeedFragPagerLayoutBinding
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.FavFragViewModelFactory
import com.andreirookie.kinosearch.di.FavoriteFilmsFragComponent
import com.andreirookie.kinosearch.di.appComponent
import com.andreirookie.kinosearch.domain.FilmFeedModel
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
    lateinit var vmFactory: FavFragViewModelFactory
    private val viewModel: FavFragViewModel by viewModels { vmFactory }

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
                val frag = FilmDetailsFragment.getInstance(id)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.feed_fragment_container, frag)
                    .addToBackStack(FilmDetailsFragment.TAG)
                    .commit()
            }
            override fun onIconClick(film: FilmFeedModel) {
                viewModel.like(film)
            }
        })

        _binding = FeedFragPagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vmJob = viewModel.eventsFlow.onEach { event ->
            handle(event)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter

            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            swipeRefreshLayout.apply {
                setColorSchemeColors(view.context.getColor(R.color.blue_200))
                setOnRefreshListener {
                    viewModel.getFav()
                    isRefreshing = false
                }
            }

            retryButton.setOnClickListener {
                viewModel.getFav()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favFilmsFlow.collect {
                    adapter.submitList(it)
                }
            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.feedState.collect { state ->
                    render(state)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFav()
    }

    private fun render(state: FeedFragState) {
        when (state) {
            is FeedFragState.Init -> {
                binding.apply {
                    progressBar.isVisible = false
                    errorGroup.isVisible = false
                }
            }
            is FeedFragState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    errorGroup.isVisible = false
                }
            }
            is FeedFragState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    errorGroup.isVisible = true
                }
            }
        }
    }

    private fun handle(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.Error -> {
                showToast(event.msg)
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
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