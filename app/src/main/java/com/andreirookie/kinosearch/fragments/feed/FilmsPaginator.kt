package com.andreirookie.kinosearch.fragments.feed

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


interface FilmsPaginator {
    fun startLoading()
    fun stopLoading()
    fun setOnListener(action: (page: Int) -> Unit)
}

class PopFilmsPaginatorImpl(
    private val layoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener(), FilmsPaginator {

    private var onLoad: ((page: Int) -> Unit)? = null
    private var isLoading = false

    override fun stopLoading() {
        isLoading = false
    }

    override fun startLoading() {
        isLoading = true
    }

    private var nextPage: Int = 1

    override fun setOnListener(action: (page: Int) -> Unit) {
        onLoad = action
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val visibleItemCount = layoutManager.childCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && nextPage < MAX_PAGES_COUNT) {

            if (firstVisibleItemPosition + visibleItemCount >= totalItemCount
                && firstVisibleItemPosition >= 0) {
                startLoading()
                nextPage++
                onLoad?.invoke(nextPage)
            }
        }
    }

    companion object {
        private const val MAX_PAGES_COUNT = 10
    }
}