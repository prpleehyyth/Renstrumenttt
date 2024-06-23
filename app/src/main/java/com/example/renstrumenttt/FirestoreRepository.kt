package com.example.renstrumenttt

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getInstruments(): List<Instrument> {
        return try {
            val snapshot = db.collection("Instrument").get().await()
            snapshot.documents.map { it.toObject(Instrument::class.java)!! }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getRentalHistory(): List<RentalHistory> {
        return try {
            val snapshot = db.collection("rentalHistory").get().await()
            snapshot.documents.map { it.toObject(RentalHistory::class.java)!! }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addRentalHistory(rentalHistory: RentalHistory) {
        try {
            db.collection("rentalHistory").add(rentalHistory).await()
        } catch (e: Exception) {
            // Handle error
        }
    }
}