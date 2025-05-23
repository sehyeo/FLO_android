package com.example.flo_android

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user: User) {

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())

                val resp: AuthResponse? = response.body()

                if (resp == null) {
                    Log.e("SIGNUP/ERROR", "응답 body가 null입니다. code: ${response.code()}")
                    return
                }

                when(resp.code) {
                    "COMMON200" -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        Log.d("SIGNUP", "HELLO")
    }

    fun login(user: User) {

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())

                val resp: AuthResponse? = response.body()

                if (resp == null) {
                    Log.e("LOGIN/ERROR", "응답 body가 null입니다. code: ${response.code()}")

                    // 🔍 서버의 메시지를 errorBody에서 추출
                    val errorMsg = response.errorBody()?.string()
                    Log.e("LOGIN/ERROR_BODY", errorMsg ?: "에러 메시지 없음")

                    loginView.onLoginFailure()

                    return
                }

                when(val code = resp.code) {
                    "COMMON200" -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })
        Log.d("LOGIN", "HELLO")
    }
}