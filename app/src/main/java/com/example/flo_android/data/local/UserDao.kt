package com.example.flo_android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flo_android.data.entities.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User?
}