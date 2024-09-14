package com.andreirookie.kinosearch.fragments.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.andreirookie.kinosearch.R
import com.andreirookie.kinosearch.databinding.FeedFragPagerLayoutBinding
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.PopFragViewModelFactory
import com.andreirookie.kinosearch.di.PopularFilmsFragComponent
import com.andreirookie.kinosearch.di.appComponent
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.domain.usecase.SearchState
import com.andreirookie.kinosearch.fragments.film.FilmDetailsFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularFilmsFragment : Fragment() {

    private var _binding: FeedFragPagerLayoutBinding? = null
    private val binding: FeedFragPagerLayoutBinding get() = _binding!!

    private var _adapter: FilmAdapter? = null
    private val adapter: FilmAdapter get() = _adapter!!

    @Inject
    lateinit var vmFactory: PopFragViewModelFactory
    private val viewModel: PopFragViewModel by viewModels { vmFactory }

    private lateinit var paginator: PopFilmsPaginatorImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        PopularFilmsFragComponent
            .getComponent(ActivityComponentHolder.getComponent(context.appComponent))
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _adapter = FilmAdapter(object : FilmCardInteractionListener {
            override fun onCardClick(id: Int) {
                val frag = FilmDetailsFragment.getInstance(id)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.feed_fragment_container, frag)
                    .addToBackStack(FilmDetailsFragment.TAG)
                    .commit()
            }
            override fun onLikeIconClick(film: FilmFeedModel) {
                viewModel.like(film)
            }
        })

        _binding = FeedFragPagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter

            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            paginator = PopFilmsPaginatorImpl(recyclerView.layoutManager as GridLayoutManager)
                .apply {
                    setOnListener { nextPage ->
                        viewModel.requestMoreFilmsByPage(nextPage)
                        viewModel.getPopFilms()

                        stopLoading()
                    }
                }
            recyclerView.addOnScrollListener(paginator)

            swipeRefreshLayout.apply {
                setColorSchemeColors(view.context.getColor(R.color.blue_200))
                setOnRefreshListener {
                    viewModel.getPopFilms()
                    isRefreshing = false
                }
            }

            retryButton.setOnClickListener {
                viewModel.getPopFilms()
            }
        }

        val searchEditText = requireActivity().findViewById<EditText>(R.id.search_edit_text)
        searchEditText.addTextChangedListener { editingText ->
            lifecycleScope.launch {
                editingText?.let { query -> viewModel.search(query.toString()) }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.feedState.collect { state ->
                    render(state)
                }
            }
        }

        viewModel.searchStateFlow
            .flowWithLifecycle(lifecycle)
            .onEach { searchState -> renderSearch(searchState) }
            .launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        println("!!!adapter.currentList.size  " + adapter.currentList.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }

    private fun renderSearch(state: SearchState) {
        when (state) {
            is SearchState.Empty -> {}
            is SearchState.Init -> {}
            is SearchState.Error -> {}
            is SearchState.Result -> {
                adapter.submitList(state.list)
            }
        }
    }

    private fun render(state: FilmFeedState<List<FilmFeedModel>>) {
        when (state) {
            is FilmFeedState.Init -> {
                binding.apply {
                    progressBar.isVisible = false
                    errorGroup.isVisible = false
                }
            }
            is FilmFeedState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    errorGroup.isVisible = false
                }
            }
            is FilmFeedState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    errorGroup.isVisible = true
                }
                Log.d(TAB_TAG, state.ex.localizedMessage ?: "Error")
                showToast(state.ex.localizedMessage ?: "Error")
            }
            is FilmFeedState.Data -> {
                binding.apply {
                    progressBar.isVisible = false
                    errorGroup.isVisible = false
                }
                adapter.submitList(state.data)
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAB_TAG = "Popular"
    }
}