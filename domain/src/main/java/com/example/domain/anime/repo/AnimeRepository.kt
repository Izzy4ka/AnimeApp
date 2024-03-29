package com.example.domain.anime.repo

import androidx.paging.PagingData
import com.example.core.base.BaseResult
import com.example.domain.anime.entity.Anime
import com.example.domain.anime.entity.AnimeEpisode
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getRandomAnime(): Flow<BaseResult<List<Anime>, String>>

    suspend fun getSearchAnime(searchBy: String): Flow<PagingData<Anime>>

    suspend fun getAnimeEpisode(id: Int): Flow<PagingData<AnimeEpisode>>

    suspend fun getAnimeInfo(id: Int): Flow<Anime>
}