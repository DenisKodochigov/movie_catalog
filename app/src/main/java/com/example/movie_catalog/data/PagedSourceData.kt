package com.example.movie_catalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.Kit
import kotlinx.coroutines.delay
import javax.inject.Inject

class PagedSourceData  @Inject constructor(val kit: Kit): PagingSource<Int, Film>() {

    private val dataRepository = DataRepository()

    override fun getRefreshKey(state: PagingState<Int, Film>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching { dataRepository.routerGetApi(page, kit) }.fold(
                onSuccess = {
                    LoadResult.Page(it, null, if(it.isEmpty()) null else page + 1)},
                onFailure = { LoadResult.Error(it)}
            )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}