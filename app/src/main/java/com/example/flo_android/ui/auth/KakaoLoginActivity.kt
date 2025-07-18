package com.example.flo_android.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_android.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginActivity : AppCompatActivity() {

    private val TAG = "KakaoLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kakaoLogin(this)
    }

    private fun kakaoLogin(context: Context) {
        // 공통 콜백
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                Toast.makeText(context, "로그인 실패: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공: ${token.accessToken}")
                Toast.makeText(context, "카카오 로그인 성공", Toast.LENGTH_SHORT).show()

                saveJwt2(token.accessToken)

                UserApiClient.instance.me { user, meError ->
                    if (meError != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", meError)
                    } else if (user != null) {
                        val nickname = user.kakaoAccount?.profile?.nickname
                        val email = user.kakaoAccount?.email
                        val profileImg = user.kakaoAccount?.profile?.profileImageUrl

                        Log.i(TAG, "닉네임: $nickname")
                        Log.i(TAG, "이메일: $email")
                        Log.i(TAG, "프로필 사진: $profileImg")

                        Toast.makeText(
                            context,
                            "닉네임: $nickname\n이메일: $email",
                            Toast.LENGTH_LONG
                        ).show()

                        saveJwt2(token.accessToken)

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }

        // 카카오톡이 설치되어 있으면 → 카카오톡으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 로그인 취소한 경우 (예: 뒤로가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공: ${token.accessToken}")
                    Toast.makeText(context, "카카오톡 로그인 성공", Toast.LENGTH_SHORT).show()

                    // TODO: 서버에 token.accessToken 넘기기 or 저장
                }
            }
        } else {
            // 카카오계정으로 바로 로그인
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.putString("login_type", "kakao")
        editor.apply()
    }
}
