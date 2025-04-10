package com.example.flo_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flo_android.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding
    lateinit var albumViewModel : AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater,container,false)
        albumViewModel = ViewModelProvider(requireActivity())[AlbumViewModel::class.java]

        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
        }

        binding.songMixoffTg.setOnClickListener {
            setMixStatus(true)
        }

        binding.songMixonTg.setOnClickListener {
            setMixStatus(false)
        }

        return binding.root
    }

    private fun setMixStatus(isActivating : Boolean){
        albumViewModel.setMixState(isActivating)

        if(isActivating){
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE
        }
        else{
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE
        }
    }

}