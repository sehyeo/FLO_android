package com.example.flo_android.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_android.R
import com.example.flo_android.databinding.FragmentPanelBinding

class PanelFragment : Fragment() {

    lateinit var binding : FragmentPanelBinding

    private var albumImgRes: Int = 0
    private var title: String = ""
    private var singer: String = ""
    private var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            albumImgRes = it.getInt("albumImgRes", R.drawable.img_album_exp)
            title = it.getString("title") ?: ""
            singer = it.getString("singer") ?: ""
            description = it.getString("description") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPanelBinding.inflate(inflater, container, false)

        binding.homePanelAlbumImg01Iv.setImageResource(albumImgRes)
        binding.homePanelAlbumImg02Iv.setImageResource(albumImgRes)
        binding.homePanelAlbumTitle01Tv.text = title
        binding.homePanelAlbumTitle02Tv.text = title
        binding.homePanelAlbumSinger01Tv.text = singer
        binding.homePanelAlbumSinger02Tv.text = singer
        binding.homePanelTitleTv.text = description

        return binding.root
    }

    companion object {
        fun newInstance(panelInfo: PanelInfo): PanelFragment {
            val fragment = PanelFragment()
            val args = Bundle().apply {
                putInt("albumImgRes", panelInfo.albumImgRes)
                putString("title", panelInfo.title)
                putString("singer", panelInfo.singer)
                putString("description", panelInfo.description)
            }
            fragment.arguments = args
            return fragment
        }
    }
}