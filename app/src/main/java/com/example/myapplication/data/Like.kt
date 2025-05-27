package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_items")
data class Like(
    @PrimaryKey val itemId: Long,
    val userEmail: String
)