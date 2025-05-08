package com.example.flo_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_android.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    fun setMiniPlayer(song: Song){
        this.song = song
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime // Seekbar의 max가 100000이므로
    }


    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null){
//            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val existingSongs = songDB.songDao().getSongs()

        if (existingSongs.isEmpty()) {
            songDB.songDao().insert(
                Song("Lilac", "아이유 (IU)", 0, 200, false, "music_lilac", R.drawable.img_album_exp2, false)
            )
            songDB.songDao().insert(
                Song("Flu", "아이유 (IU)", 0, 200, false, "music_flu", R.drawable.img_album_exp2, false)
            )
            songDB.songDao().insert(
                Song("Butter", "방탄소년단 (BTS)", 0, 190, false, "music_butter", R.drawable.img_album_exp, false)
            )
            songDB.songDao().insert(
                Song("Next Level", "에스파 (AESPA)", 0, 210, false, "music_next", R.drawable.img_album_exp3, false)
            )
            songDB.songDao().insert(
                Song("Boy with Luv", "방탄소년단 (BTS)", 0, 230, false, "music_boy", R.drawable.img_album_exp4, false)
            )
            songDB.songDao().insert(
                Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 240, false, "music_bboom", R.drawable.img_album_exp5, false)
            )
            songDB.songDao().insert(
                Song("Weekend", "태연 (Tae Yeon)", 0, 234, false, "music_lilac", R.drawable.img_album_exp6, false)
            )
        }

        // 새로 추가할 곡 목록
        val newSongs = listOf(
            Song("Celebrity", "아이유 (IU)", 0, 200, false, "music_lilac", R.drawable.img_album_exp2, false),
            Song("Coin", "아이유 (IU)", 0, 200, false, "music_lilac", R.drawable.img_album_exp2, false),
            Song("소우주 (Mikrokosmos)", "방탄소년단 (BTS)", 0, 230, false, "music_boy", R.drawable.img_album_exp4, false),
            Song("Make It Right", "방탄소년단 (BTS)", 0, 230, false, "music_boy", R.drawable.img_album_exp4, false)
        )

        newSongs.forEach { newSong ->
            val exists = existingSongs.any { it.title == newSong.title && it.singer == newSong.singer }
            if (!exists) {
                songDB.songDao().insert(newSong)
            }
        }

        val updatedSongs = songDB.songDao().getSongs()
        Log.d("DB data", updatedSongs.toString())

        for (song in updatedSongs) {
            Log.d("SongDebug", "ID=${song.id}, Title=${song.title}")
        }
    }


}