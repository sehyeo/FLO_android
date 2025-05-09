package com.example.flo_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_android.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }

            override fun onPlayClick(album: Album) {
                Log.d("HomeFragment", "▶ 버튼 클릭됨 - 앨범: ${album.title}, 가수: ${album.singer}")
                val songDB = SongDatabase.getInstance(requireContext())!!
                val song = songDB.songDao().getSongs().firstOrNull {
                    it.title == album.title && it.singer == album.singer
                }
                song?.let {
                    Log.d("HomeFragment", "선택된 곡 ID: ${it.id}, 제목: ${it.title}")
                    requireActivity().getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE).edit {
                        putInt("songId", it.id)
                    }

                    // 미니 플레이어 즉시 갱신
                    val mainActivity = activity as? MainActivity
                    mainActivity?.setMiniPlayer(it)
                }
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val panelAdapter = PanelVPAdapter(this)
        val panels = listOf(
            PanelInfo(R.drawable.img_album_exp, "In My Bed", "bear", "매혹적인 음색의 여성 보컬\n팝"),
            PanelInfo(R.drawable.img_album_exp2, "LILAC", "아이유", "감성 가득한 밤을 위한\n선곡"),
            PanelInfo(R.drawable.img_album_exp, "Ditto", "NewJeans", "중독적인 비트의 글로벌\nK-POP")
        )

        panels.forEach { panelInfo ->
            panelAdapter.addFragment(PanelFragment.newInstance(panelInfo))
        }

        binding.homePanelVp.adapter = panelAdapter
        binding.homePanelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePanelIndicator.setViewPager(binding.homePanelVp)

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}