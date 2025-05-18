package com.example.flo_android

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ChartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val title = TextView(requireContext()).apply {
            text = "FLO 차트  19시 기준\n최근 24시간 집계, FLO 최고 인기곡 차트!"
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
        }

        layout.addView(title)

        val listView = ListView(requireContext())
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, (0..9).map { "Item $it" })
        listView.adapter = adapter

        layout.addView(listView)

        return layout
    }
}
