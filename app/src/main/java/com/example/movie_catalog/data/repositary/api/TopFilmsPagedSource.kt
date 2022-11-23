package com.example.movie_catalog.data.repositary.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_catalog.data.repositary.api.home.top.TopFilmDTO
import javax.inject.Inject

class TopFilmsPagedSource @Inject constructor(private val type: String): PagingSource <Int, TopFilmDTO>() {

    override fun getRefreshKey(state: PagingState<Int, TopFilmDTO>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopFilmDTO> {
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