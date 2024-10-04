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

        return FilmViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        holder.bindTo(film)
    }

    override fun onBindViewHolder(
        holder: FilmViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.bindLike(getItem(position).isLiked)
            }
        }
    }

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
    override fun getChangePayload(oldItem: FilmFeedModel, newItem: FilmFeedModel): Any? {
        return if (oldItem.isLiked != newItem.isLiked) true else null
    }
}

//enum class ItemCardChangeType(val changedValue: Any?) {
//    LIKED(changedValue = true)
//}