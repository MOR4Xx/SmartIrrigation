package com.ifmaker.smartirrigation.data.Model


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

data class Historico(
    val data: String = "",
    val hora: String = "",
    val quantidade: Double = 0.0,
    val modo: String = "",
    val usuario: String = "",
    val timestamp: Date? = null
)

class HistoricoRepository() {
    val db = FirebaseFirestore.getInstance()
    val collectionPath = "historico"

    suspend fun getHistorico(): List<Historico> {
        Log.d("Arrumando saporra", "entrou getHistorico")

        val result = db.collection(collectionPath).get().await()

        Log.d("Arrumando saporra", result.documents.toString())
        // Isso converte tudo sozinho
        return result.toObjects(Historico::class.java)

//        return listOf(
//            Historico(
//                data = "12/11/2025",
//                hora = "08:32",
//                qtdAgua = 50.0,
//                modo = "Automático",
//                usuario = "Jorge",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "12/11/2025",
//                hora = "14:10",
//                qtdAgua = 30.0,
//                modo = "Manual",
//                usuario = "Erick",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "13/11/2025",
//                hora = "09:50",
//                qtdAgua = 70.0,
//                modo = "Automático",
//                usuario = "Sistema",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "13/11/2025",
//                hora = "17:22",
//                qtdAgua = 40.0,
//                modo = "Manual",
//                usuario = "Jorge",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "14/11/2025",
//                hora = "07:10",
//                qtdAgua = 55.0,
//                modo = "Automático",
//                usuario = "Erick",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "14/11/2025",
//                hora = "19:48",
//                qtdAgua = 25.0,
//                modo = "Manual",
//                usuario = "Admin",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "15/11/2025",
//                hora = "10:05",
//                qtdAgua = 80.0,
//                modo = "Automático",
//                usuario = "Sistema",
//                timestamp = Date()
//            ),
//            Historico(
//                data = "15/11/2025",
//                hora = "16:35",
//                qtdAgua = 35.0,
//                modo = "Manual",
//                usuario = "Jorge",
//                timestamp = Date()
//            )
//        )

    }
}
