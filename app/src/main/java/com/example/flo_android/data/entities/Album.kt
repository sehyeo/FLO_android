package com.example.flo_android.data.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var title: String? = "",
    var singer: String? = "",
    @DrawableRes var coverImg: Int? = null
)
