package com.example.renstrumenttt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.renstrumenttt.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var instrument: Instrument
    private var days: Int = 0
    private var quantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the instrument data from the intent
        instrument = intent.getParcelableExtra("instrument") ?: Instrument()
        days = intent.getIntExtra("days", 0)
        quantity = intent.getIntExtra("quantity", 0)


        // Setup payment options
        binding.buttonCreditCard.setOnClickListener {
            proceedToCheckout("BCA")
        }

        binding.buttonPaypal.setOnClickListener {
            proceedToCheckout("BNI")
        }

        // Add more payment options as needed
    }

    private fun proceedToCheckout(paymentMethod: String) {
        val intent = Intent(this, CheckoutActivity::class.java)
        intent.putExtra("instrument", instrument)
        intent.putExtra("days", days)
        intent.putExtra("quantity", quantity)
        intent.putExtra("paymentMethod", paymentMethod)
        startActivity(intent)
    }
}