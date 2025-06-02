package com.example.flo_android.data.remote

import com.example.flo_android.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/join")
    fun signUp(@Body user: User): Call<AuthResponse>
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @GET("/test")
    fun test(@Header("Authorization") token: String): Call<AuthTestResponse>
}