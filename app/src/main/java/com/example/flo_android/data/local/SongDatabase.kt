package com.example.flo_android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flo_android.data.entities.Album
import com.example.flo_android.data.entities.Like
import com.example.flo_android.data.entities.Song
import com.example.flo_android.data.entities.User


@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 13)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    )
                        .fallbackToDestructiveMigration() // 기존 데이터 삭제 허용
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instance
        }
    }
}