package com.andreirookie.kinosearch.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.andreirookie.kinosearch.domain.Film
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding

class FilmAdapter(
    private val listener: FilmCardInterActionListener
) : ListAdapter<Film, FilmViewHolder>(FilmDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmListItemLayoutBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.bind(film)
    }
}

class FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}