package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.Historico
import kotlinx.coroutines.tasks.await

class HistoricoRepository() {
    val db = FirebaseFirestore.getInstance()
    val collectionPath = "historico"

    suspend fun getHistorico(): List<Historico> {
        Log.d("Arrumando saporra", "entrou getHistorico")

        val result = db.collection(collectionPath).get().await()

        Log.d("Arrumando saporra", result.documents.toString())
        // Isso converte tudo sozinho
        return result.toObjects(Historico::class.java)
    }
}