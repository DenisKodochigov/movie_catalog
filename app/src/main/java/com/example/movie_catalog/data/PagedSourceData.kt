package com.example.movie_catalog.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_catalog.entity.Film
import kotlinx.coroutines.delay
import javax.inject.Inject

class PagedSourceData  @Inject constructor(): PagingSource<Int, Film>() {

    private val dataRepository = DataRepository()
    override fun getRefreshKey(state: PagingState<Int, Film>) = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            delay(4000)
            dataRepository.routerGetApi(page) }.fold(
            onSuccess = {
                LoadResult.Page(it, null, if(it.isEmpty()) null else page + 1)},
            onFailure = { LoadResult.Error(it)}
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}