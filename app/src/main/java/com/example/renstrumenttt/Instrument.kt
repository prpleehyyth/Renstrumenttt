package com.example.renstrumenttt

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Instrument(
    val name: String = "",
    val desc: String = "",
    val imageUrl: String = "",
    val pricePerDay: Double = 0.0,
    val stock: Int = 0
) : Parcelable
