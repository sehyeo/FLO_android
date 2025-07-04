package com.example.flo_android.ui.main.locker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_android.R
import com.example.flo_android.data.local.SongDatabase
import com.example.flo_android.data.entities.Song
import com.example.flo_android.databinding.ItemSongBinding

class SavedSongRVAdapter() : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    private val selectedStates = mutableMapOf<Int, Boolean>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAll(select: Boolean) {
        for (i in songs.indices) {
            selectedStates[i] = select
        }
        notifyDataSetChanged()
    }

    fun getSelectedSongs(): List<Song> {
        return songs.filterIndexed { index, _ -> selectedStates[index] == true }
    }

    fun removeSelectedSongs() {
        for (i in songs.size - 1 downTo 0) {
            if (selectedStates[i] == true) {
                songs.removeAt(i)
                selectedStates.remove(i)
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongSwitch.isChecked = song.isChecked

            val isSelected = selectedStates[adapterPosition] == true
            binding.root.setBackgroundColor(
                if (isSelected) ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
                else ContextCompat.getColor(binding.root.context, android.R.color.transparent)
            )

            binding.itemSongSwitch.setOnCheckedChangeListener { _, isChecked ->
                song.isChecked = isChecked
                // DB에 저장
                val db = SongDatabase.getInstance(binding.root.context)!!
                db.songDao().updateIsCheckedById(isChecked, song.id)
            }
        }
    }
}