package com.example.renstrumenttt

data class RentalHistory(
    val instrumentName: String = "",
    val imageUrl: String = "",
    val rentalDays: Int = 0,
    val rentalStartDate: String = "",
    val rentalEndDate: String = "",
    val totalPrice: Double = 0.0,
    val paymentMethod: String = ""
)
