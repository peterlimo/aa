package com.example.kcmav1.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

class ForumRepository(private val ForumDAO: ForumDAO)
{
    val allForums:LiveData<List<Forum>> =ForumDAO.readAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addForum(forum: Forum)
    {
        ForumDAO.AddForum(forum)

    }

        @Query("DELETE FROM forum_table")
    suspend fun deleteAll() {
        }

}