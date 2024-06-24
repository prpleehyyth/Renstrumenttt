package com.example.renstrumenttt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var instrument: Instrument

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the instrument data from the intent
        instrument = intent.getParcelableExtra("instrument") ?: Instrument()

        // Set the instrument details
        setInstrumentDetails()

        // Setup the button click listener
        binding.buttonProceedToPayment.setOnClickListener {
            proceedToPayment()
        }
    }

    private fun setInstrumentDetails() {
        binding.tvMusicDetailName.text = instrument.name
        binding.tvMusicDetailDesc.text = instrument.desc
        binding.tvMusicDetailPrice.text = "Price per Day: $${instrument.pricePerDay}"
        binding.tvStock.text = "Stock: ${instrument.stock}"

        Glide.with(this)
            .load(instrument.imageUrl)
            .into(binding.ivMusicDetailImage)
    }

    private fun proceedToPayment() {
        val days = binding.editTextDays.text.toString().toIntOrNull()
        val quantity = binding.editTextQuantity.text.toString().toIntOrNull()

        if (days != null && days > 0 && quantity != null && quantity > 0) {
            if (quantity <= instrument.stock) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("instrument", instrument)
                intent.putExtra("days", days)
                intent.putExtra("quantity", quantity)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Not enough stock available", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter valid number of days and quantity", Toast.LENGTH_SHORT).show()
        }
    }
}