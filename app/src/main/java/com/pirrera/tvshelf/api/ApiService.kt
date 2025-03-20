package com.pirrera.tvshelf.api

import com.pirrera.tvshelf.data.SeriesData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR")
    suspend fun getSeries(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_genres=10759&with_watch_providers=8|337|350|119|283|15&watch_region=FR")
    suspend fun getSeriesByAction(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_genres=10765&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=2")
    suspend fun getSeriesByFictionFantasy(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_genres=80&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=3")
    suspend fun getSeriesByCrime(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_genres=35&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4")
    suspend fun getSeriesByComedy(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_genres=18&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4")
    suspend fun getSeriesByDrama(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=12")
    suspend fun getSeriesForSearch12(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page11")
    suspend fun getSeriesForSearch11(): SeriesData


    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=10")
    suspend fun getSeriesForSearch10(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=9")
    suspend fun getSeriesForSearch9(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=8")
    suspend fun getSeriesForSearch8(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=7")
    suspend fun getSeriesForSearch7(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=6")
    suspend fun getSeriesForSearch6(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=5")
    suspend fun getSeriesForSearch5(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=4")
    suspend fun getSeriesForSearch4(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=3")
    suspend fun getSeriesForSearch3(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=2")
    suspend fun getSeriesForSearch2(): SeriesData

    @GET("tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=1")
    suspend fun getSeriesForSearch1(): SeriesData

    @GET("../search/tv?api_key=232c2294d3026daa9a8273d9c624c4d8&query=High+Fidelity&language=fr-FR")
    suspend fun getSeriesForSearch0(): SeriesData

    @GET("tv")
    suspend fun getASerie(
        @Query("query") film: String,
        @Query("api_key") apiKey: String = "232c2294d3026daa9a8273d9c624c4d8",
        @Query("language") language: String = "fr-FR"
    ): SeriesData


}

//https://api.themoviedb.org/3/discover/tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR&page=1