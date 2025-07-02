package com.example.flo_android.ui.main.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_android.databinding.ItemCategoryBinding
import androidx.core.graphics.toColorInt
import com.example.flo_android.R

class SearchRVAdapter(
    private val items: List<String>,
    private var selectedIndex: Int,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String, position: Int) {
            binding.categoryBtn.text = text

            if (position == selectedIndex) {
                binding.categoryBtn.setBackgroundResource(R.drawable.bg_selected)
                binding.categoryBtn.setTextColor(Color.WHITE)
            } else {
                binding.categoryBtn.setBackgroundResource(R.drawable.bg_unselected)
                binding.categoryBtn.setTextColor("#000000".toColorInt())
            }

            binding.root.setOnClickListener {
                onItemClick(position)
                notifyItemChanged(selectedIndex)
                selectedIndex = position
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}