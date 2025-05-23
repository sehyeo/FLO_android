package com.example.flo_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_android.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val categories = listOf("차트", "영상", "장르", "상황", "분위기", "오디오")
    private var selectedIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SearchRVAdapter(categories, selectedIndex) { index ->
            selectedIndex = index
            showContentForCategory(categories[index])
        }
        binding.categoryRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRv.adapter = adapter

        showContentForCategory(categories[selectedIndex])
    }

    private fun showContentForCategory(category: String) {
        val fragment = when (category) {
            "차트" -> ChartFragment()
            else -> LookFragment()
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.category_content_container, fragment)
            .commit()
    }
}
