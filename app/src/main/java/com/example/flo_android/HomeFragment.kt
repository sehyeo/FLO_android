package com.example.flo_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm , AlbumFragment())
//                .commitAllowingStateLoss()
//        }

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
}