package com.example.flo_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_android.databinding.FragmentChartBinding

class ChartFragment : Fragment() {

    lateinit var binding: FragmentChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)

        val chartList = listOf(
            "10cm - 너에게 닿기를",
            "WOODZ - Drowning",
            "제니 (JENNIE) - like JENNIE",
            "조째즈 - 모르시나요(PROD.로코베리)",
            "BOYNEXTDOOR - 오늘만 I LOVE YOU",
            "G-DRAGON - TOO BAD (feat.Anderson .Paak)",
            "G-DRAGON - HOME SWEET HOME (feat. 태양, 대성)",
            "황가람 - 나는 반딧불",
            "aespa - Whiplash",
            "우디 (Woody) - 어제보다 슬픈 오늘",
            "오반(OVAN) - Flower",
            "IVE (아이브) - REBEL HEART",
            "DAY6 (데이식스) - HAPPY"
        )

        val adapter = ChartRVAdapter(chartList)
        binding.chartListview.adapter = adapter
        binding.chartListview.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }
}

