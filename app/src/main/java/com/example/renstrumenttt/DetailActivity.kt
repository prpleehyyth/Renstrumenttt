package com.example.renstrumenttt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.renstrumenttt.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari intent
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val image = intent.getStringExtra("image")
        val priceperday = intent.getDoubleExtra("priceperday", 0.0)

        // Set data ke views
        binding.tvMusicDetailName.text = name
        binding.tvMusicDetailDesc.text = desc
        binding.tvMusicDetailPrice.text = "Price/Day: $priceperday"

        Glide.with(this)
            .load(image)
            .into(binding.ivMusicDetailImage)
    }
}