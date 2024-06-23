package com.example.renstrumenttt

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ItemMusicBinding

class MusicAdapter(private var instruments: List<Instrument>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(private val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(instrument: Instrument) {
            binding.tvMusicName.text = instrument.name
            binding.tvMusicDesc.text = instrument.desc
            binding.tvPricePerDay.text = "Price/Day: ${instrument.pricePerDay}"
            Glide.with(binding.ivMusicImage.context)
                .load(instrument.imageUrl)
                .into(binding.ivMusicImage)

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("name", instrument.name)
                    putExtra("desc", instrument.desc)
                    putExtra("image", instrument.imageUrl)
                    putExtra("priceperday", instrument.pricePerDay)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(instruments[position])
    }

    override fun getItemCount() = instruments.size

    fun updateList(newList: List<Instrument>) {
        instruments = newList
        notifyDataSetChanged()
    }
}