package com.andreirookie.kinosearch.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreirookie.kinosearch.databinding.FeedFragBinding

class FeedFragment : Fragment() {

    private var _binding: FeedFragBinding? = null
    private val binding: FeedFragBinding get() = _binding!!

    private var _adapter: MovieAdapter? = null
    private val adapter: MovieAdapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _adapter = MovieAdapter()
        _binding = FeedFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter

        }


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _adapter = null
    }


    companion object {
        const val TAG = "FeedFragment"
    }
}