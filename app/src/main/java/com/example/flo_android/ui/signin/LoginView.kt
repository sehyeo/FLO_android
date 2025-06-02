package com.example.flo_android

import com.example.flo_android.data.remote.Result

interface LoginView {
    fun onLoginSuccess(code : String, result : Result)
    fun onLoginFailure()
}