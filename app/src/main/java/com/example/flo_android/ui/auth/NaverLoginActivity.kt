package com.example.flo_android.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo_android.ui.main.MainActivity
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class NaverLoginActivity : Activity() {

    private val TAG = "NaverLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        naverLogin(this)
    }

    private fun naverLogin(context: Context) {
        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                val accessToken = NaverIdLoginSDK.getAccessToken()
                Log.i(TAG, "네이버계정으로 로그인 성공: $accessToken")
                Toast.makeText(context, "네이버 로그인 성공", Toast.LENGTH_SHORT).show()

                saveJwt2(accessToken ?: "")

                val intent = Intent(this@NaverLoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("accessToken", accessToken)
                startActivity(intent)
                finish()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.e("네이버 로그인 실패", "httpStatus: $httpStatus, message: $message")
                finish()
            }

            override fun onError(errorCode: Int, message: String) {
                Log.e("네이버 로그인 에러", "errorCode: $errorCode, message: $message")
                finish()
            }
        })
    }

    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2", Context.MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", jwt)
        editor.putString("login_type", "naver")
        editor.apply()
    }
}