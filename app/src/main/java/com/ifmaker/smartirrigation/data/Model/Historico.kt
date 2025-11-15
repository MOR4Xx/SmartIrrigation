package com.ifmaker.smartirrigation.data.Model


import java.util.Date

data class Historico(
    val data: String,
    val hora: String,
    val qtdAgua: Double,
    val modo: String,
    val usuario: String,
    val timestamp: Date? = null
)

class HistoricoRepository() {
//    val db = FirebaseFirestore.getInstance()
    val collectionPath = "Historico"

    fun getHistorico(): List<Historico> {
//        Log.d("Arrumando saporra", "entrou getHistorico")
//        val result = db.collection(collectionPath).get()
//            .await()
//        Log.d("Arrumando saporra", result.documents.toString())
//        return result.documents.map { doc ->
//            Historico(
//                data = doc.getString("data") ?: "",
//                hora = doc.getString("hora") ?: "",
//                qtdAgua = doc.getDouble("qtdAgua") ?: 0.0,
//                modo = doc.getString("modo") ?: "",
//                usuario = doc.getString("usuario") ?: ""
//            )
//        }
        return listOf(
            Historico(
                data = "12/11/2025",
                hora = "08:32",
                qtdAgua = 50.0,
                modo = "Automático",
                usuario = "Jorge",
                timestamp = Date()
            ),
            Historico(
                data = "12/11/2025",
                hora = "14:10",
                qtdAgua = 30.0,
                modo = "Manual",
                usuario = "Erick",
                timestamp = Date()
            ),
            Historico(
                data = "13/11/2025",
                hora = "09:50",
                qtdAgua = 70.0,
                modo = "Automático",
                usuario = "Sistema",
                timestamp = Date()
            ),
            Historico(
                data = "13/11/2025",
                hora = "17:22",
                qtdAgua = 40.0,
                modo = "Manual",
                usuario = "Jorge",
                timestamp = Date()
            ),
            Historico(
                data = "14/11/2025",
                hora = "07:10",
                qtdAgua = 55.0,
                modo = "Automático",
                usuario = "Erick",
                timestamp = Date()
            ),
            Historico(
                data = "14/11/2025",
                hora = "19:48",
                qtdAgua = 25.0,
                modo = "Manual",
                usuario = "Admin",
                timestamp = Date()
            ),
            Historico(
                data = "15/11/2025",
                hora = "10:05",
                qtdAgua = 80.0,
                modo = "Automático",
                usuario = "Sistema",
                timestamp = Date()
            ),
            Historico(
                data = "15/11/2025",
                hora = "16:35",
                qtdAgua = 35.0,
                modo = "Manual",
                usuario = "Jorge",
                timestamp = Date()
            )
        )

    }
}
