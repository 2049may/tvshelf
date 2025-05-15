package com.pirrera.tvshelf.api

import com.pirrera.tvshelf.data.SeriesData
import retrofit2.http.GET
import retrofit2.http.Query
import com.pirrera.tvshelf.BuildConfig

interface ApiService {
    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&append_to_response=episode_groups")
    suspend fun getSeries(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=10759&with_watch_providers=8|337|350|119|283|15&watch_region=FR&append_to_response=episode_groups")
    suspend fun getSeriesByAction(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=10765&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=2&append_to_response=episode_groups")
    suspend fun getSeriesByFictionFantasy(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=80&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=3&append_to_response=episode_groups")
    suspend fun getSeriesByCrime(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=35&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4&append_to_response=episode_groups")
    suspend fun getSeriesByComedy(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=18&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4&append_to_response=episode_groups")
    suspend fun getSeriesByDrama(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_genres=10762&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=2&append_to_response=episode_groups")
    suspend fun getSeriesByKids(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=12&append_to_response=episode_groups")
    suspend fun getSeriesForSearch12(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page11&append_to_response=episode_groups")
    suspend fun getSeriesForSearch11(): SeriesData


    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=10&append_to_response=episode_groups")
    suspend fun getSeriesForSearch10(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=9&append_to_response=episode_groups")
    suspend fun getSeriesForSearch9(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=8&append_to_response=episode_groups")
    suspend fun getSeriesForSearch8(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=7&append_to_response=episode_groups")
    suspend fun getSeriesForSearch7(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=6&append_to_response=episode_groups")
    suspend fun getSeriesForSearch6(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=5&append_to_response=episode_groups")
    suspend fun getSeriesForSearch5(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4&append_to_response=episode_groups")
    suspend fun getSeriesForSearch4(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=3&append_to_response=episode_groups")
    suspend fun getSeriesForSearch3(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=2&append_to_response=episode_groups")
    suspend fun getSeriesForSearch2(): SeriesData

    @GET("tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=1&append_to_response=episode_groups")
    suspend fun getSeriesForSearch1(): SeriesData

    @GET("../search/tv?api_key="+BuildConfig.TVSHELF_API_KEY+"&query=High+Fidelity&language=fr-FR&append_to_response=episode_groups")
    suspend fun getSeriesForSearch0(): SeriesData

    @GET("tv")
    suspend fun getASerie(
        @Query("query") film: String,
        @Query("api_key") apiKey: String = BuildConfig.TVSHELF_API_KEY,
        @Query("append_to_response") elt : String = "episode_groups",
        @Query("language") language: String = "en-EN"
    ): SeriesData


}

