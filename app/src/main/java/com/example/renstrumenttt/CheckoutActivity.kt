package com.example.renstrumenttt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ActivityCheckoutBinding
import com.google.firebase.firestore.FirebaseFirestore


class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var instrument: Instrument
    private var days: Int = 0
    private var paymentMethod: String = ""
    private var totalPrice: Double = 0.0
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the instrument data from the intent
        instrument = intent.getParcelableExtra("instrument") ?: Instrument()
        days = intent.getIntExtra("days", 0)
        paymentMethod = intent.getStringExtra("paymentMethod") ?: ""

        // Set checkout details
        setCheckoutDetails()

        // Setup proceed to rental history button
        binding.buttonProceedToHistory.setOnClickListener {
            saveProofOfPaymentUrlToFirestore()
        }
    }

    private fun setCheckoutDetails() {
        binding.textViewInstrumentName.text = instrument.name
        Glide.with(this)
            .load(instrument.imageUrl)
            .into(binding.imageViewInstrument)

        binding.textViewDays.text = "Rental Duration: $days days"
        totalPrice = days * instrument.pricePerDay
        binding.textViewTotalPrice.text = "Total Price: $%.2f".format(totalPrice)
        binding.textViewPaymentMethod.text = "Payment Method: $paymentMethod"

        // Set rental dates (today as start date and calculated end date)
        val startDate = System.currentTimeMillis()
        val endDate = startDate + days * 24 * 60 * 60 * 1000L // Calculate end date
        binding.textViewStartDate.text = "Start Date: ${java.text.SimpleDateFormat("yyyy-MM-dd").format(startDate)}"
        binding.textViewEndDate.text = "End Date: ${java.text.SimpleDateFormat("yyyy-MM-dd").format(endDate)}"
    }

    private fun saveProofOfPaymentUrlToFirestore() {
        val startDate = System.currentTimeMillis()
        val endDate = startDate + days * 24 * 60 * 60 * 1000L

        val rentalDetails = hashMapOf(
            "instrumentName" to instrument.name,
            "rentalStartDate" to startDate,
            "rentalEndDate" to endDate,
            "paymentMethod" to paymentMethod,
            "rentalDays" to days,
            "totalPrice" to totalPrice
        )

        firestore.collection("rentalHistory")
            .add(rentalDetails)
            .addOnSuccessListener {
                proceedToRentalHistory()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to save rental details: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun proceedToRentalHistory() {
        val intent = Intent(this, RentalHistoryActivity::class.java)
        startActivity(intent)
    }
}