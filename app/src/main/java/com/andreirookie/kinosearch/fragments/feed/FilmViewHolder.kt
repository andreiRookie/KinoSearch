package com.andreirookie.kinosearch.fragments.feed

import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding
import com.andreirookie.kinosearch.domain.FilmFeedModel
import com.bumptech.glide.Glide

class FilmViewHolder(
    private val binding: FilmListItemLayoutBinding,
    private val listener: FilmCardInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun provideBinding(): FilmListItemLayoutBinding {
        return binding
    }

    fun bindTo(film: FilmFeedModel) {
        with(binding) {
            filmTitle.text = film.title
            filmGenre.text = film.genre
            filmYear.text = "(${film.issueYear})"
            likeIcon.isChecked = film.isLiked

            Glide.with(root.context)
                .load(film.posterUrlPreview)
                .into(filmImage)

            root.setOnClickListener {
                listener.onCardClick(film.id)
            }

            likeIcon.setOnClickListener {
                listener.onIconClick(film)
            }
        }
    }
}

interface FilmCardInteractionListener {
    fun onCardClick(id: Int)
    fun onIconClick(film: FilmFeedModel)
}