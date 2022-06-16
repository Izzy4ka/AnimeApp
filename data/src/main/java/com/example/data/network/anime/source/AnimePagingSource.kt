package com.example.data.network.anime.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.anime.entity.Anime
import com.example.domain.anime.entity.AnimePaging

typealias AnimePageLoader = suspend (page: Int, limit: Int) -> AnimePaging

class AnimePagingSource(
    private val loader: AnimePageLoader,
    private val limit: Int
) : PagingSource<Int, Anime>() {


    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        val anchorPos = state.anchorPosition ?: return null

        val page = state.closestPageToPosition(anchorPos) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        val pageIndex = params.key ?: 0
        return try {
            val animeLoader = loader.invoke(pageIndex, limit)
            return LoadResult.Page(
                data = animeLoader.anime,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (animeLoader.has_next_page) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}