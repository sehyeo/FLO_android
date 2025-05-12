package com.example.flo_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_android.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {

    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB: SongDatabase
    lateinit var savedSongRVAdapter: SavedSongRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart(){
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.lockerSavedSongRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedSongRVAdapter = SavedSongRVAdapter()
        binding.lockerSavedSongRecyclerView.adapter = savedSongRVAdapter

        savedSongRVAdapter.setMyItemClickListener(object : SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })

        savedSongRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }

    fun selectAllSongs(select: Boolean) {
        savedSongRVAdapter.selectAll(select)
    }

    fun deleteSelectedSongs() {
        val selectedSongs = savedSongRVAdapter.getSelectedSongs()
        for (song in selectedSongs) {
            songDB.songDao().updateIsLikeById(false, song.id)
        }
        savedSongRVAdapter.removeSelectedSongs()
    }
}