package com.example.renstrumenttt

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ItemRentalHistoryBinding

class RentalHistoryAdapter(private var rentalHistoryList: List<RentalHistory>) :
    RecyclerView.Adapter<RentalHistoryAdapter.RentalHistoryViewHolder>() {

    inner class RentalHistoryViewHolder(val binding: ItemRentalHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rentalHistory: RentalHistory) {
            binding.apply {
                textViewInstrumentName.text = rentalHistory.instrumentName
                textViewRentalDays.text = "Rental Duration: ${rentalHistory.rentalDays} days"
                textViewStartDate.text = "Start Date: ${rentalHistory.rentalStartDate}"
                textViewEndDate.text = "End Date: ${rentalHistory.rentalEndDate}"
                textViewTotalPrice.text = "Total Price: $${rentalHistory.totalPrice}"

                Glide.with(imageViewInstrument.context)
                    .load(rentalHistory.imageUrl)
                    .into(imageViewInstrument)

                buttonViewInvoice.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, InvoiceActivity::class.java).apply {
                        putExtra("instrumentName", rentalHistory.instrumentName)
                        putExtra("imageUrl", rentalHistory.imageUrl)
                        putExtra("days", rentalHistory.rentalDays)
                        putExtra("totalPrice", rentalHistory.totalPrice)
                        putExtra("paymentMethod", rentalHistory.paymentMethod)
                        putExtra("startDate", rentalHistory.rentalStartDate)
                        putExtra("endDate", rentalHistory.rentalEndDate)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalHistoryViewHolder {
        val binding = ItemRentalHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RentalHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RentalHistoryViewHolder, position: Int) {
        holder.bind(rentalHistoryList[position])
    }

    override fun getItemCount() = rentalHistoryList.size
}