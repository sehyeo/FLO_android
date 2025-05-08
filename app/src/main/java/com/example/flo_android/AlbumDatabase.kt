package com.example.flo_android

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase: RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        private var instance: AlbumDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlbumDatabase? {
            if (instance == null) {
                synchronized(AlbumDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlbumDatabase::class.java,
                        "album-database"
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