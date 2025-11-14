package com.ifmaker.smartirrigation.data.Model

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.Date

data class Historico(
    val data: String,
    val hora: String,
    val qtdAgua: Double,
    val modo: String,
    val usuario: String,
    @com.google.firebase.firestore.ServerTimestamp
    val timestamp: Date? = null
)

class HistoricoRepository() {
    val db = FirebaseFirestore.getInstance()
    val collectionPath = "Historico"

    suspend fun getHistorico(): List<Historico> {
        Log.d("Arrumando saporra", "entrou getHistorico")
        val result = db.collection(collectionPath).get()
            .await()
        Log.d("Arrumando saporra", result.documents.toString())
        return result.documents.map { doc ->
            Historico(
                data = doc.getString("data") ?: "",
                hora = doc.getString("hora") ?: "",
                qtdAgua = doc.getDouble("qtdAgua") ?: 0.0,
                modo = doc.getString("modo") ?: "",
                usuario = doc.getString("usuario") ?: ""
            )
        }
    }
}
