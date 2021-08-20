package com.example.kcmav1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Forum::class),version = 1,exportSchema = false)
abstract class ForumRoomDatabase:RoomDatabase() {
    abstract fun roomDao():ForumDAO

    companion object{
        @Volatile
        private var INSTANCE:ForumRoomDatabase?=null

        fun getDatabase(context: Context):ForumRoomDatabase{
            val tempInstance= INSTANCE
            if (tempInstance!=null) {
                return tempInstance
            }
            return INSTANCE?: synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    ForumRoomDatabase::class.java,
                    "room_database"
                ).build()

                INSTANCE=instance

              return  instance
            }

        }
    }
}
