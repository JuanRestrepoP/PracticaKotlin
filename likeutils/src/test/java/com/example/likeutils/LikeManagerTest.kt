package com.example.likeutils

import com.example.likeutils.data.Like
import com.example.likeutils.data.LikeDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LikeManagerTest {

    private lateinit var likeDao: LikeDao

    @Before
    fun setup() {
        likeDao = mockk(relaxed = true)
    }

    @Test
    fun `toggleLike should insert like when item is not liked`() = runTest {
        coEvery { likeDao.isItemLiked(1L, "test@email.com") } returns false
        coEvery { likeDao.insert(any()) } just runs

        val result = LikeManager.toggleLike(1L, "test@email.com", likeDao)

        coVerify { likeDao.insert(Like(itemId = 1L, userEmail = "test@email.com")) }
        assertTrue(result)
    }

    @Test
    fun `toggleLike should delete like when item is already liked`() = runTest {
        coEvery { likeDao.isItemLiked(1L, "test@email.com") } returns true
        coEvery { likeDao.deleteLike(1L, "test@email.com") } just runs

        val result = LikeManager.toggleLike(1L, "test@email.com", likeDao)

        coVerify { likeDao.deleteLike(1L, "test@email.com") }
        assertFalse(result)
    }

    @Test
    fun `isLiked should return the correct value`() = runTest {
        coEvery { likeDao.isItemLiked(1L, "test@email.com") } returns true

        val result = LikeManager.isLiked(1L, "test@email.com", likeDao)

        assertTrue(result)
    }
}
