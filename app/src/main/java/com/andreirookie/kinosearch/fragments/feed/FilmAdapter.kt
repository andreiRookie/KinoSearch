package com.andreirookie.kinosearch.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding

class FilmAdapter(
    private val listener: FilmCardInteractionListener
) : ListAdapter<FilmFeedModel, FilmViewHolder>(FilmDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmListItemLayoutBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.bindTo(film)
    }
}

class FilmDiffCallback : DiffUtil.ItemCallback<FilmFeedModel>() {
    override fun areItemsTheSame(oldItem: FilmFeedModel, newItem: FilmFeedModel): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: FilmFeedModel, newItem: FilmFeedModel): Boolean {
        return oldItem == newItem
    }
}