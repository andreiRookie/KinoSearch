package com.andreirookie.kinosearch.fragments.feed

import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.kinosearch.R
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.bumptech.glide.Glide

class FilmViewHolder(
    private val binding: FilmListItemLayoutBinding,
    private val listener: FilmCardInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(film: FilmFeedModel) {
        with(binding) {
            filmTitle.text = film.title
            filmGenre.text = film.genre
            filmYear.text = root.context.getString(R.string.film_year, film.issueYear)
            likeIcon.isChecked = film.isLiked

            Glide.with(root.context)
                .load(film.posterUrlPreview)
                .into(filmImage)

            root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onCardClick(film.id)
                }
            }

            likeIcon.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    listener.onLikeIconClick(film)
                }
            }
        }
    }
}

interface FilmCardInteractionListener {
    fun onCardClick(id: Int)
    fun onLikeIconClick(film: FilmFeedModel)
}