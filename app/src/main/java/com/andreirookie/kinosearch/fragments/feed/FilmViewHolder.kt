package com.andreirookie.kinosearch.fragments.feed

import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding
import com.andreirookie.kinosearch.domain.Film
import com.bumptech.glide.Glide

class FilmViewHolder(
    private val binding: FilmListItemLayoutBinding,
    private val listener: FilmCardInterActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(film: Film) {
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
        }
    }
}
interface FilmCardInterActionListener {
    fun onCardClick(id: Int)
}