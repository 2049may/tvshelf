package com.pirrera.tvshelf.api

import com.pirrera.tvshelf.data.SeriesData
import retrofit2.http.GET

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
}