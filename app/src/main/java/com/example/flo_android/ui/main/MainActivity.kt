package com.example.flo_android.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_android.ui.main.home.HomeFragment
import com.example.flo_android.ui.main.locker.LockerFragment
import com.example.flo_android.ui.main.look.LookFragment
import com.example.flo_android.R
import com.example.flo_android.ui.main.search.SearchFragment
import com.example.flo_android.ui.song.SongActivity
import com.example.flo_android.data.database.SongDatabase
import com.example.flo_android.data.entities.Album
import com.example.flo_android.data.entities.Song
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
        inputDummyAlbums()
        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

        Log.d("MAIN/JWT_TO_SERVER", getJwt().toString())

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

    private fun getJwt(): String? {
        val spf = this.getSharedPreferences("auth2", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getString("jwt", "")
    }

    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        if (song == null) {
            Log.e("ERROR", "song is null")
            return
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)

    }

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!

        //songDB.songDao().deleteAllSongs()
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song("Lilac", "아이유 (IU)", 0, 200, false, "music_lilac",
                R.drawable.img_album_exp2, false, false, 1)
        )
        songDB.songDao().insert(
            Song("Flu", "아이유 (IU)", 0, 200, false, "music_flu",
                R.drawable.img_album_exp2, false, false, 1)
        )
        songDB.songDao().insert(
            Song("Butter", "방탄소년단 (BTS)", 0, 190, false, "music_butter",
                R.drawable.img_album_exp, false, false, 2)
        )
        songDB.songDao().insert(
            Song("Next Level", "에스파 (AESPA)", 0, 210, false, "music_next",
                R.drawable.img_album_exp3, false, false, 3)
        )
        songDB.songDao().insert(
            Song("Boy with Luv", "방탄소년단 (BTS)", 0, 230, false, "music_boy",
                R.drawable.img_album_exp4, false, false, 4)
        )
        songDB.songDao().insert(
            Song("BBoom BBoom", "모모랜드 (MOMOLAND)", 0, 240, false, "music_bboom",
                R.drawable.img_album_exp5, false, false, 5)
        )
        songDB.songDao().insert(
            Song("Weekend", "태연 (Tae Yeon)", 0, 234, false, "music_lilac",
                R.drawable.img_album_exp6, false, false, 6)
        )
        songDB.songDao().insert(
            Song("Celebrity", "아이유 (IU)", 0, 200, false, "music_lilac",
                R.drawable.img_album_exp2, false, false, 1),
        )

        songDB.songDao().insert(
            Song("Coin", "아이유 (IU)", 0, 200, false, "music_lilac",
                R.drawable.img_album_exp2, false, false, 1)
        )

        songDB.songDao().insert(
            Song("소우주 (Mikrokosmos)", "방탄소년단 (BTS)", 0, 230, false, "music_boy",
                R.drawable.img_album_exp4, false, false, 4)
        )

        songDB.songDao().insert(
            Song("Make It Right", "방탄소년단 (BTS)", 0, 230, false, "music_boy",
                R.drawable.img_album_exp4, false, false, 4)
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!

       // songDB.albumDao().deleteAllAlbums()
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(1, "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2)
        )

        songDB.albumDao().insert(
            Album(2, "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp)
        )

        songDB.albumDao().insert(
            Album(3, "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(4, "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp4)
        )

        songDB.albumDao().insert(
            Album(5, "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5)
        )

        songDB.albumDao().insert(
            Album(6, "Weekend", "태연 (Taeyeon)", R.drawable.img_album_exp6)
        )

    }

}