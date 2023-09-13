package com.andreirookie.kinosearch.fragments.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andreirookie.kinosearch.databinding.FilmDetailsFragLayoutBinding
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.FilmDetailsFragComponent
import com.andreirookie.kinosearch.di.appComponent
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsFragment : Fragment() {

    private var _binding: FilmDetailsFragLayoutBinding? = null
    private val binding: FilmDetailsFragLayoutBinding get() = _binding!!

    @Inject
    lateinit var viewModel: FilmDetailsFragViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FilmDetailsFragComponent.getComponent(ActivityComponentHolder.getComponent(context.appComponent))
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FilmDetailsFragLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmId = arguments?.getInt(FILM_ID)
        filmId?.let { id -> viewModel.loadFilmInfo(id) }

        binding.apply {
            theFilmDetailsRetryButton.setOnClickListener {
                filmId?.let { id -> viewModel.loadFilmInfo(id) }
            }

            goBackButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: FilmDetailsFragState) {
        when (state) {
            is FilmDetailsFragState.Init -> {
                binding.apply {
                    theFilmDetailsProgressBar.isVisible = false
                    theFilmDetailsErrorGroup.isVisible = false
                }
            }
            is FilmDetailsFragState.Loading -> {
                binding.apply {
                    theFilmDetailsProgressBar.isVisible = true
                    theFilmDetailsErrorGroup.isVisible = false
                }
            }
            is FilmDetailsFragState.Error -> {
                binding.apply {
                    theFilmDetailsProgressBar.isVisible = false
                    theFilmDetailsErrorGroup.isVisible = true
                }
            }
            is FilmDetailsFragState.Data -> {
                binding.apply {
                    theFilmDetailsProgressBar.isVisible = false
                    theFilmDetailsErrorGroup.isVisible = false

                    Glide.with(root.context).load(state.film.posterUrl)
                        .into(binding.theFilmPosterImage)

                    theFilmTitle.text = state.film.title
                    theFilmDescription.text = state.film.description
                    theFilmCountry.text = state.film.country
                    theFilmLength.text = state.film.filmLength
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilmDetailsFragment"

        private const val FILM_ID = "film_id"
        fun getInstance(filmId: Int): FilmDetailsFragment {
            val fragment = FilmDetailsFragment()
            val bundle = bundleOf(FILM_ID to filmId)
            fragment.arguments = bundle
            return fragment
        }
    }
}