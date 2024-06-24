package com.example.renstrumenttt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.renstrumenttt.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicAdapter
    private var instrumentList = mutableListOf<Instrument>()

    private val db = FirebaseFirestore.getInstance()
    private val instrumentsCollection = db.collection("Instrument")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadInstruments()
        setupBottomNavigationView()
    }

    private fun setupRecyclerView() {
        adapter = MusicAdapter(instrumentList) { instrument ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("instrument", instrument)
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = this@MainActivity.adapter
        }
    }

    private fun loadInstruments() {
        lifecycleScope.launch {
            try {
                val snapshot = instrumentsCollection.get().await()
                val instruments = snapshot.toObjects(Instrument::class.java)
                instrumentList.clear()
                instrumentList.addAll(instruments)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle error loading instruments
            }
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.bottom_home

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> true
                R.id.bottom_history -> {
                    startActivity(Intent(this, RentalHistoryActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
