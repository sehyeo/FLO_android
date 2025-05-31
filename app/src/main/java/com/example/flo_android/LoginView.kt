package com.example.flo_android

interface LoginView {
    fun onLoginSuccess(code : String, result : Result)
    fun onLoginFailure()
}