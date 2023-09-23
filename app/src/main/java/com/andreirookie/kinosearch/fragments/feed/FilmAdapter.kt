package com.andreirookie.kinosearch.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.andreirookie.kinosearch.databinding.FilmListItemLayoutBinding
import com.andreirookie.kinosearch.domain.FilmFeedModel

class FilmAdapter(
    private val listener: FilmCardInteractionListener
) : ListAdapter<FilmFeedModel, FilmViewHolder>(FilmDiffCallback()) {

    private var _binding: FilmListItemLayoutBinding? = null
    private val binding: FilmListItemLayoutBinding get() = _binding!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = FilmListItemLayoutBinding.inflate(inflater, parent, false)

        val holder = FilmViewHolder(binding, listener)

        // TODO delete
//        holder.itemView.setOnClickListener {
//            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
//
//                val film = getItem(holder.bindingAdapterPosition)
//                listener.onCardClick(film.id)
//            }
//        }
//        holder.provideBinding().likeIcon.setOnCheckedChangeListener { _, _ ->
//            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
//                val film = getItem(holder.bindingAdapterPosition)
//                listener.onIconClick(film)
//            }
//        }

        return holder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.bindTo(film)
    }


    // TODO: does it affect??
    override fun onViewRecycled(holder: FilmViewHolder) {
        super.onViewRecycled(holder)
        _binding = null
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