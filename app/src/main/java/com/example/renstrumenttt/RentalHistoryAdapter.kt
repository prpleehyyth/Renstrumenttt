package com.example.renstrumenttt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ItemRentalHistoryBinding

class RentalHistoryAdapter(private var rentalHistoryList: List<RentalHistory>) :
    RecyclerView.Adapter<RentalHistoryAdapter.RentalHistoryViewHolder>() {

    inner class RentalHistoryViewHolder(val binding: ItemRentalHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalHistoryViewHolder {
        val binding = ItemRentalHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RentalHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RentalHistoryViewHolder, position: Int) {
        val rentalHistory = rentalHistoryList[position]
        holder.binding.apply {
            textViewInstrumentName.text = rentalHistory.instrumentName
            textViewRentalDays.text = "Rental Duration: ${rentalHistory.rentalDays} days"
            textViewStartDate.text = "Start Date: ${rentalHistory.rentalStartDate}"
            textViewEndDate.text = "End Date: ${rentalHistory.rentalEndDate}"
            textViewTotalPrice.text = "Total Price: $${rentalHistory.totalPrice}"

            Glide.with(imageViewInstrument.context)
                .load(rentalHistory.imageUrl)
                .into(imageViewInstrument)
        }
    }

    override fun getItemCount() = rentalHistoryList.size
}