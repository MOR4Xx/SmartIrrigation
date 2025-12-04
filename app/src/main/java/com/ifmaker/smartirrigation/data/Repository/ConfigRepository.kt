package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.CoeficienteCultura
import com.ifmaker.smartirrigation.data.Model.ParametrosIrrigacao
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
            Log.d("Latitude: ", "Latitude: ${result.getDouble("latitude")}")
            return result.getDouble("latitude")
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao obter latitude", e)
        }
        return 0.0
    }

    fun setLatitude(latitude: Double, callback: (Double) -> Unit) {
        Log.d("Latitude: ", "Latitude: $latitude")

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

    fun getCultura(callback: (List<String>) -> Unit) {
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

    fun setPlantio(cultura: String, fase: String) {
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

    fun getDadosCultura(callback: (String, String) -> Unit) {
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

    fun getParametros(callback: (ParametrosIrrigacao) -> Unit) {
        db.collection(collectionPath).document("config_Irrigacao").get()
            .addOnSuccessListener { document ->
                document.toObject(ParametrosIrrigacao::class.java)?.let {
                    callback(it)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao obter parametros", e)
            }
    }

    fun setParametros(parametrosIrrigacao: ParametrosIrrigacao) {
        db.collection(collectionPath).document("config_Irrigacao")
            .set(parametrosIrrigacao)
            .addOnSuccessListener {
                Log.d("Firestore", "Parâmetros de Irrigação atualizados com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar parâmetros de Irrigação", e)
            }
    }

    fun setModoIrrigacao(modo: String) {
        val docRef = db.collection(collectionPath).document(documentId)
        docRef.update("modo", modo)
            .addOnSuccessListener {
                Log.d("Firestore", "Modo de Irrigação alterado com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar modo de Irrigação", e)
            }
    }

    fun getModoIrrigacao(callback: (String) -> Unit) {
        db.collection(collectionPath).document(documentId).get()
            .addOnSuccessListener { document ->
                val modo = document.getString("modo")
                if (modo != null) {
                    callback(modo)
                }
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao obter modo de Irrigação", e)
            }
    }

    fun setAutorizacaoIrrigacaoAutomatica(autorizacao: Boolean) {
        val docRef = db.collection(collectionPath).document(documentId)
        docRef.update("autorizacao", autorizacao)
            .addOnSuccessListener {
                Log.d("Firestore", "Permissão alterada com sucesso")
            }
    }

    fun setQuantidadeAguaManual(quantidade: Int) {
        val docRef = db.collection(collectionPath).document(documentId)
        docRef.update("quantidade_agua", quantidade)
            .addOnSuccessListener {
                Log.d("Firestore", "Quantidade alterada com sucesso")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Erro ao atualizar quantidade", e)
            }
    }

    fun getKc(cultura: String, callback: (CoeficienteCultura?) -> Unit) {
        db.collection("coeficientes").document(cultura).get()
            .addOnSuccessListener { document ->
                val dados = document.toObject(CoeficienteCultura::class.java)
                callback(dados)
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Erro ao obter dados do documento", exception)
            }
    }

    fun setKc(novoKc: String){
        val docref = db.collection(collectionPath).document("config_Irrigacao")

        docref.update("kc", novoKc).addOnSuccessListener {
                Log.d("Firebase", "KC atualizado com sucesso para: $novoKc")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Erro ao atualizar KC", e)
            }
    }

}


