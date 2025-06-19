package com.example.likeutils.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likedItem: Like)

    @Query("SELECT * FROM liked_items WHERE userEmail = :userEmail")
    suspend fun getLikedItemsByUser(userEmail: String): List<Like>

    @Query("SELECT EXISTS(SELECT 1 FROM liked_items WHERE itemId = :itemId AND userEmail = :userEmail)")
    suspend fun isItemLiked(itemId: Long, userEmail: String): Boolean

    @Query("DELETE FROM liked_items WHERE itemId = :itemId AND userEmail = :userEmail")
    suspend fun deleteLike(itemId: Long, userEmail: String)
}