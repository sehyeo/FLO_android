package com.example.flo_android.ui.main.fire

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_android.R
import com.example.flo_android.data.entities.Fire

class FireAdapter (private val context: Context, private val fireList:ArrayList<Fire>):
    RecyclerView.Adapter<FireAdapter.FireViewHolder>(){


    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.fire_layout, parent, false)

        return FireViewHolder(view)
    }

    // 값 갯수 리턴
    override fun getItemCount(): Int { return fireList.size }

    // 데이터 설정
    override fun onBindViewHolder(holder: FireViewHolder, position: Int) {

        val fire: Fire = fireList[position]

        holder.fireTitleText.text = fire.fireTitle
        holder.fireSingerText.text = fire.fireSinger


    }

    class FireViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val fireTitleText: TextView = itemView.findViewById(R.id.fire_title_text)
        val fireSingerText: TextView = itemView.findViewById(R.id.fire_singer_text)
    }
}