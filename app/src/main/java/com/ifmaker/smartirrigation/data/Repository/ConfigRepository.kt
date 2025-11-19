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
            Log.e("Firestore", "Erro ao obter latitude", e)
        }
        return 0.0
    }

    suspend fun  setLatitude(latitude: Double, callback: (Double) -> Unit){
        Log.d("Latitude: ","Latitude: $latitude")

        val docRef = db.collection(collectionPath).document(documentId)

        docRef.update("latitude", latitude)
            .addOnSuccessListener {
                Log.d("Firestore", "Latitude alterada com sucesso")
                callback(latitude)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar", e)
            }
    }

    fun getCultura(callback: (List<String>) -> Unit){
        val plantioList = mutableListOf<String>()

        db.collection("coeficientes").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val nomeDoPlantio = document.getString("cultura")

                    if (nomeDoPlantio != null) {
                        plantioList.add(nomeDoPlantio)
                    }

                    Log.d("Firestore", "${document.id} => $nomeDoPlantio")
                }

                callback(plantioList)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao obter plantio", e)
                callback(emptyList())
            }
    }

    fun setPlantio(cultura: String,fase:String){
        val plantio = hashMapOf(
            "cultura" to cultura,
            "fase" to fase
        )

        db.collection("configuracao").document("config_kc")
            .set(plantio).addOnSuccessListener {
                Log.d("Firestore", "Plantio adicionado com sucesso")
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao adicionar plantio", e)

            }
    }

    fun getDadosCultura(callback: (String, String) -> Unit){
        db.collection(collectionPath).document("config_kc").get()
            .addOnSuccessListener { document ->
                val cultura = document.getString("cultura")
                val fase = document.getString("fase")

                if (cultura != null && fase != null) {
                    callback(cultura, fase)
                } else {
                    callback("", "")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao obter dados da cultura", e)
                callback("", "")
            }

    }

}