package com.example.flo_android.ui.login

import com.example.flo_android.data.remote.Result

interface LoginView {
    fun onLoginSuccess(code : String, result : Result)
    fun onLoginFailure()
}