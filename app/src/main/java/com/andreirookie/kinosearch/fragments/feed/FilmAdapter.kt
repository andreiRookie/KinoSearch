package com.andreirookie.kinosearch.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreirookie.kinosearch.data.models.Film
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding
import com.bumptech.glide.Glide

class FilmAdapter : ListAdapter<Film, FilmViewHolder>(FilmDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmListItemLayoutBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.bind(film)
    }
}

class FilmViewHolder(
    private val binding: FilmListItemLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(film: Film) {
        with(binding) {
            filmTitle.text = film.title
            filmGenre.text = film.genre
            filmYear.text = "(${film.issueYear})"
            likeIcon.isChecked = film.isLiked

            Glide.with(root.context)
                .load(film.posterUrlPreview)
                .into(filmImage)
        }
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