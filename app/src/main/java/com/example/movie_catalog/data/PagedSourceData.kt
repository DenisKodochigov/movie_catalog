package com.example.movie_catalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import kotlinx.coroutines.delay
import javax.inject.Inject

class PagedSourceData  @Inject constructor(val kit: Kit): PagingSource<Int, Linker>() {

    private val dataRepository = DataRepository()

    override fun getRefreshKey(state: PagingState<Int, Linker>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Linker> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching { dataRepository.routerGetApi(page, kit) }.fold(
            onSuccess = {
//                delay(10000)
                LoadResult.Page(it, null, if(it.isEmpty()) null else page + 1)},
            onFailure = { LoadResult.Error(it)}
            )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}