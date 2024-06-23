package com.example.renstrumenttt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renstrumenttt.databinding.ActivityRentalHistoryBinding
import com.google.firebase.firestore.FirebaseFirestore

class RentalHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRentalHistoryBinding
    private lateinit var adapter: RentalHistoryAdapter
    private var rentalHistoryList: MutableList<RentalHistory> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RentalHistoryAdapter(rentalHistoryList)
        binding.recyclerViewRentalHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRentalHistory.adapter = adapter

        fetchRentalHistoryFromFirestore()
    }

    private fun fetchRentalHistoryFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("rentalHistory")
            .get()
            .addOnSuccessListener { result ->
                rentalHistoryList.clear()
                for (document in result) {
                    val rentalHistory = document.toObject(RentalHistory::class.java)
                    rentalHistoryList.add(rentalHistory)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }
}