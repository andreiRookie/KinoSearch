package com.andreirookie.kinosearch.fragments.feed

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


interface FilmsPaginater {
    fun startLoading()
    fun stopLoading()
    fun setOnListener(action: () -> Unit)
}

class PopFilmsPaginaterImpl(
    private val layoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener(), FilmsPaginater {

    private var onLoad: (() -> Unit)? = null

    private var isLoading = false

    override fun stopLoading() {
        isLoading = false
    }

    override fun startLoading() {
        isLoading = true
    }

    var nextPage: Int = 1
        private set

    override fun setOnListener(action: () -> Unit) {
        onLoad = action
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val visibleItemCount = layoutManager.childCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (firstVisibleItemPosition + visibleItemCount > totalItemCount - SPAN_ITEM_COUNT_OFFSET) {
            if (!isLoading) {

                nextPage++

                if (nextPage <= MAX_PAGES_COUNT) {
                    onLoad?.invoke()
                }
            }
        }
    }

    companion object {
        private const val SPAN_ITEM_COUNT_OFFSET = 3
        private const val MAX_PAGES_COUNT = 10
    }
}