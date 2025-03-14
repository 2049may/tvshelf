package com.pirrera.tvshelf.api

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
        private const val BASE_URL = "https://api.themoviedb.org/3/discover/"
        val api : ApiService by lazy{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
}

// ######### SOLUTION DONNER PAR L'API DES SERIES #############
//    val client = OkHttpClient()
//
//    val request = Request.Builder()
//        .url("https://api.themoviedb.org/3/discover/tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR")
//        .get()
//        .addHeader("accept", "application/json")
//        .build()
//
//    val response = client.newCall(request).execute()


// ######## SOLUTION COURS ##########

//private const val BASE_URL = "https://api.themoviedb.org/3/discover/tv?api_key=232c2294d3026daa9a8273d9c624c4d8&sort_by=popularity.desc&with_watch_providers=8|337|350|119|283|15&watch_region=FR"
//val api : ApiService by lazy{
//    Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)
//}