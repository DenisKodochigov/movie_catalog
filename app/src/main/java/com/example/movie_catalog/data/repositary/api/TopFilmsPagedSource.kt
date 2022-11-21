package com.example.movie_catalog.data.repositary.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_catalog.entity.home.top.TopFilm
import javax.inject.Inject

class TopFilmsPagedSource @Inject constructor(val type: String): PagingSource <Int, TopFilm>() {

    override fun getRefreshKey(state: PagingState<Int, TopFilm>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopFilm> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching { retrofitApi.getTop(page,type).films }.fold(
            onSuccess = { LoadResult.Page(it, null, if(it.isEmpty()) null else page + 1)},
            onFailure = { LoadResult.Error(it)}
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}