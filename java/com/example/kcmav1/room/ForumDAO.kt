package com.example.kcmav1.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForumDAO {

    @Query("SELECT * FROM forum_table ORDER BY forum_name ASC")
    fun getAlphabetizedWords():Flow<List<Forum>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun AddForum(forum: Forum)


    @Query("SELECT * FROM forum_table ORDER BY id ASC")
     fun readAllData():LiveData<List<Forum>>

}