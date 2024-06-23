package com.example.renstrumenttt

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ItemMusicBinding

class MusicAdapter(private val instrumentList: List<Instrument>, private val clickListener: (Instrument) -> Unit) :
    RecyclerView.Adapter<MusicAdapter.InstrumentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstrumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InstrumentViewHolder, position: Int) {
        val instrument = instrumentList[position]
        holder.bind(instrument, clickListener)
    }

    override fun getItemCount(): Int {
        return instrumentList.size
    }

    class InstrumentViewHolder(private val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(instrument: Instrument, clickListener: (Instrument) -> Unit) {
            binding.apply {
                tvMusicName.text = instrument.name
                tvMusicDesc.text = instrument.desc
                tvPricePerDay.text = "Price per Day: $${instrument.pricePerDay}"
                Glide.with(binding.ivMusicImage.context)
                    .load(instrument.imageUrl)
                    .into(binding.ivMusicImage)
                root.setOnClickListener { clickListener(instrument) }
            }
        }
    }
}