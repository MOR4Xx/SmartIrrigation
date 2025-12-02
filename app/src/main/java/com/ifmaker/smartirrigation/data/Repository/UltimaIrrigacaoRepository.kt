package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import kotlinx.coroutines.tasks.await


class UltimaIrrigacaoRepository {
    val db = FirebaseFirestore.getInstance()
    val collectionPath = "configuracao"


    suspend fun getUltimaIrrigacao(callback: (UltimaIrrigacao?) -> Unit) {
        try {
            val document = db.collection(collectionPath)
                .document("config_ultima_irrigacao")
                .get()
                .await()

            callback(document.toObject(UltimaIrrigacao::class.java))
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao obter última irrigação", e)
        }
    }
}