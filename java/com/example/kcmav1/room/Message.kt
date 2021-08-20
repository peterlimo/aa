package com.example.kcmav1.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message(@PrimaryKey(autoGenerate = true) val id:Int, val viewType:Int, @ColumnInfo(name="message") val message:String)
