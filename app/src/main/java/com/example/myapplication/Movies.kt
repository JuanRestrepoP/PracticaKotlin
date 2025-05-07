package com.example.myapplication

data class Movies (
    val page: Int?,
    val results: List<Movie>
)

data class Movie (
    val id: Long,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?
)
