package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ConfigRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionPath = "configuracao"
    private val documentId = "config_variaveis"

    suspend fun getLatitude(): Double? {
        try {
            val result = db.collection(collectionPath)
                .document(documentId)
                .get()
                .await()
            Log.d("Latitude: ","Latitude: ${result.getDouble("latitude")}")
            return result.getDouble("latitude")
        } catch (e: Exception) {
            null
        }
        return 0.0
    }

    suspend fun  setLatitude(latitude: Double) {
        Log.d("Latitude: ","Latitude: $latitude")




    }

}