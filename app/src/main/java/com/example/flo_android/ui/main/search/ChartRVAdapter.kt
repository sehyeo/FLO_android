package com.example.flo_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_android.databinding.ItemChartBinding

class ChartRVAdapter(private val chartList: List<String>) :
    RecyclerView.Adapter<ChartRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemChartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.itemChartTitleTv.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = chartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chartList[position])
    }
}
