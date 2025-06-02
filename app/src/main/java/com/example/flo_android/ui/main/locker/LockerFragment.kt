package com.example.flo_android.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo_android.databinding.FragmentLockerBinding
import com.example.flo_android.ui.main.MainActivity
import com.example.flo_android.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")
    private lateinit var lockerAdapter: LockerVPAdapter
    val bottomSheetFragment = BottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

        binding.lockerSelectAllTv.setOnClickListener {
            lockerAdapter.savedSongFragment.selectAllSongs(true)
        }

        binding.lockerSelectAllImgIv.setOnClickListener {
            lockerAdapter.savedSongFragment.selectAllSongs(true)
        }

        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }


        setupBottomSheetListener()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun setupBottomSheetListener() {
        bottomSheetFragment.setBottomSheetListener(object :
            BottomSheetFragment.BottomSheetListener {
            override fun onDeleteSelected() {
                lockerAdapter.savedSongFragment.deleteSelectedSongs()
            }
        })

        binding.lockerSelectAllTv.setOnLongClickListener {
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetDialog")
            true
        }

        binding.lockerSelectAllImgIv.setOnLongClickListener {
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetDialog")
            true
        }
    }

    private fun getJwt():Int {
        val spf = activity?.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    private fun getJwt2(): String? {
        val spf = activity?.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
        return spf?.getString("jwt", null)
    }

    private fun getLoginType(): String? {
        val spf = activity?.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
        return spf?.getString("login_type", null)
    }

    private fun initViews() {
        val jwt: String? = getJwt2()
        if (jwt.isNullOrEmpty()) {
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
//        val jwt : Int = getJwt()
//        if (jwt == 0) {
//            binding.lockerLoginTv.text = "로그인"
//            binding.lockerLoginTv.setOnClickListener {
//                startActivity(Intent(activity, LoginActivity::class.java))
//            }
//        } else {
//            binding.lockerLoginTv.text = "로그아웃"
//            binding.lockerLoginTv.setOnClickListener {
//                // 로그아웃 진행
//                logout()
//                startActivity(Intent(activity, MainActivity::class.java))
//            }
//        }
    }

    private fun logout() {
        when (getLoginType()) {
            "kakao" -> kakaoLogout()
            else -> basicLogout()
        }
    }

    private fun basicLogout() {
        val spf = activity?.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.clear()
        editor.apply()
    }

    private fun kakaoLogout() {
        com.kakao.sdk.user.UserApiClient.instance.logout { error ->
            if (error != null) {
                android.util.Log.e("LockerFragment", "카카오 로그아웃 실패", error)
            } else {
                android.util.Log.i("LockerFragment", "카카오 로그아웃 성공")

                val spf = activity?.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)
                val editor = spf!!.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }


}