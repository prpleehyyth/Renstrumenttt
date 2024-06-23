package com.example.renstrumenttt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renstrumenttt.databinding.ActivityMainBinding
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
    }

    private fun setupRecyclerView() {
        adapter = MusicAdapter(instrumentList) { instrument ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("instrument", instrument)
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
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
}