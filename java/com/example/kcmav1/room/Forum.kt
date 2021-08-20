package com.example.kcmav1.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forum_table")
    data class Forum(@PrimaryKey(autoGenerate = true) val id:Int ,@ColumnInfo(name="forum_name") var room:String)
