package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.Usuario

class UsuarioRepository () {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val collectionPath = "usuarios"

    fun login(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { e ->
                callback(false, e.localizedMessage)
            }
    }

    fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        permissao: String,
        callback: (Boolean, String?) -> Unit
    ) {
        Log.d("teste","usuario cadastrado")
//        auth.createUserWithEmailAndPassword(email, senha)
//            .addOnSuccessListener { result ->
//
//                val uid = result.user?.uid ?: return@addOnSuccessListener
//
//                val usuario = Usuario(
//                    uid = uid,
//                    nome = nome,
//                    email = email,
//                    tipo = permissao
//                )
//
//                db.collection("usuarios")
//                    .document(uid)
//                    .set(usuario)
//                    .addOnSuccessListener {
//                        callback(true, null)
//                    }
//                    .addOnFailureListener { e ->
//                        callback(false, e.localizedMessage)
//                    }
//            }
//            .addOnFailureListener { e ->
//                callback(false, e.localizedMessage)
//            }
    }


}