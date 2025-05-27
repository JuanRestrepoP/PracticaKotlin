package com.example.myapplication.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likedItem: Like)

    @Query("SELECT * FROM liked_items WHERE userEmail = :userEmail")
    suspend fun getLikedItemsByUser(userEmail: String): List<Like>

    @Query("SELECT EXISTS(SELECT 1 FROM liked_items WHERE itemId = :itemId AND userEmail = :userEmail)")
    suspend fun isItemLiked(itemId: String, userEmail: String): Boolean
}