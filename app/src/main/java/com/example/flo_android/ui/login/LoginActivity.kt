package com.example.flo_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_android.ui.signup.SignUpActivity
import com.example.flo_android.data.remote.AuthService
import com.example.flo_android.data.remote.LoginRequest
import com.example.flo_android.data.remote.Result
import com.example.flo_android.databinding.ActivityLoginBinding
import com.example.flo_android.ui.auth.GoogleLoginActivity
import com.example.flo_android.ui.auth.KakaoLoginActivity
import com.example.flo_android.ui.auth.NaverLoginActivity
import com.example.flo_android.ui.main.MainActivity


class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        binding.loginKakakoLoginIv.setOnClickListener {
            startActivity(Intent(this, KakaoLoginActivity::class.java))
        }

        binding.loginNaverLoginIv.setOnClickListener {
            startActivity(Intent(this, NaverLoginActivity::class.java))
        }

        binding.loginAppleLoginIv.setOnClickListener {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
        }

    }

    private fun login(){
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        if (binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        }

        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()

//        val songDB = SongDatabase.getInstance(this)!!
//        val user = songDB.userDao().getUser(email, pwd)
//
//        user?.let {
//            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, $user")
//            //saveJwt(user.id)
//            startMainActivity()
//        }

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(LoginRequest(email, pwd))

//        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

//    private fun saveJwt(jwt:Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt", jwt)
//        editor.apply()
//    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.putString("login_type", "basic")
        editor.apply()
    }

    override fun onLoginSuccess(code: String, result: Result) {
        when(code) {
            "COMMON200" -> {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                saveJwt2(result.accessToken)
                AuthService().test(result.accessToken)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure() {
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }
}