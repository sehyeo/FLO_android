package com.example.flo_android

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Song::class], version = 3)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao

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