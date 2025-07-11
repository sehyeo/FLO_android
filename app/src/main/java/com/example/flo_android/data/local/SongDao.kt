package com.example.flo_android.data.local

import androidx.room.*
import com.example.flo_android.data.entities.Song

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("DELETE FROM SongTable")
    fun deleteAllSongs()

    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id = :id")
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike= :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean,id: Int)

    @Query("SELECT * FROM SongTable WHERE isLike= :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>

    @Query("UPDATE SongTable SET isChecked = :checked WHERE id = :id")
    fun updateIsCheckedById(checked: Boolean, id: Int)

}