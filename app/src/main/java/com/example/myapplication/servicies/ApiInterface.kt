package com.example.myapplication.servicies

import com.example.myapplication.Movies
import retrofit2.http.GET
import retrofit2.Call

interface ApiInterface {
    @GET("top_rated?api_key=c5c47722a4adcc77f6e84f28a48b857a")
    fun getMovies(): Call<Movies>
}