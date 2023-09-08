package com.andreirookie.kinosearch.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreirookie.kinosearch.databinding.MovieListItemLayoutBinding
import com.andreirookie.kinosearch.models.Movie

class MovieAdapter : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemLayoutBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}

class MovieViewHolder(
    private val binding: MovieListItemLayoutBinding
) : ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {
            movieTitle.text = movie.title
            movieGenre.text = movie.genre
            movieYear.text = "(${movie.issueYear})"
            likeIcon.isChecked = movie.isLiked
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}