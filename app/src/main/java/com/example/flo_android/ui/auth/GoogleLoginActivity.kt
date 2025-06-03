package com.example.flo_android.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_android.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class GoogleLoginActivity : AppCompatActivity() {

    private val TAG = "GoogleLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleLogin(this)
    }

    private fun googleLogin(context: Context) {
        // 콜백 등록 및 결과 처리
        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.i(TAG, "구글 로그인 성공: ${account.email}")

                    val idToken = account.idToken
                    if (idToken != null) {
                        saveJwt2(idToken)

                        Toast.makeText(context, "구글 로그인 성공", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "ID 토큰이 null입니다.", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: ApiException) {
                    Log.e(TAG, "구글 로그인 실패", e)
                    Toast.makeText(this, "로그인 실패: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }

        val signInIntent = GlobalApplication.googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", jwt)
        editor.putString("login_type", "google")
        editor.apply()
    }
}
