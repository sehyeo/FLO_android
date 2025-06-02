package com.example.flo_android.data.remote

import android.util.Log
import com.example.flo_android.ui.login.LoginView
import com.example.flo_android.ui.signup.SignUpView
import com.example.flo_android.data.entities.User
import com.example.flo_android.utils.getRetrofit
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

    fun login(request: LoginRequest) {

        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)

        loginService.login(request).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())

                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null && loginResponse.code == "COMMON200") {
                        loginView.onLoginSuccess(loginResponse.code, loginResponse.result!!)
                    } else {
                        loginView.onLoginFailure()
                    }
                } else {
                    // 실패 응답 처리
                    val errorMsg = response.errorBody()?.string()
                    Log.e("LOGIN/ERROR_BODY", errorMsg ?: "에러 메시지 없음")
                    loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
                loginView.onLoginFailure()
            }

        })
        Log.d("LOGIN", "HELLO")
    }

    fun test(jwt: String) {
        val testService = getRetrofit().create(AuthRetrofitInterface::class.java)
        testService.test("Bearer $jwt").enqueue(object : Callback<AuthTestResponse> {
            override fun onResponse(call: Call<AuthTestResponse>, response: Response<AuthTestResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Log.d("TEST", "result: ${response.body()?.result}")
                } else {
                    Log.e("TEST", "code: ${response.body()?.code}, message: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<AuthTestResponse>, t: Throwable) {
                Log.e("TEST", "네트워크 오류: ${t.message}")
            }
        })
    }
}