package com.example.myapplication.data

import androidx.room.Embedded

data class MovieWithLikes(
    @Embedded val movie: MovieEntity,
    val likeCount: Int
)