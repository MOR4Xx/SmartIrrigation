package com.ifmaker.smartirrigation.data.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import kotlinx.coroutines.tasks.await


class UltimaIrrigacaoRepository {
    val db = FirebaseFirestore.getInstance()
    val collectionPath = "configuracao"


    suspend fun getUltimaIrrigacao(): UltimaIrrigacao? {
        return try {
            val snapshot = db.collection(collectionPath)
                .document("config_ultima_irrigacao")
                .get()
                .await()
            snapshot.toObject(UltimaIrrigacao::class.java)
        } catch (e: Exception) {
            null
        }
    }
}