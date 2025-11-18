package com.ifmaker.smartirrigation.data.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ifmaker.smartirrigation.data.Model.Usuario

class UsuarioRepository() {
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

    fun getPermissao(callback: (String?) -> Unit) {
        val uid = auth.currentUser?.uid

        db.collection("usuarios")
            .document(uid.toString())
            .get()
            .addOnSuccessListener { document ->
                val permissao = document.getString("tipo")
                callback(permissao.toString())
            }
            .addOnFailureListener {
                callback("Erro ao obter permissão")
            }
    }

    fun getNomeUsuario(callback: (String?) -> Unit) {
        val uid = auth.currentUser?.uid

        db.collection("usuarios")
            .document(uid.toString()).get()
            .addOnSuccessListener { document ->
                val nome = document.getString("nome")
                callback(nome.toString())
            }
            .addOnFailureListener {
                callback("Erro ao pegar nome de usuario")
            }
    }


        fun cadastrarUsuario(
            nome: String,
            email: String,
            senha: String,
            tipo: String,
            callback: (Boolean, String?) -> Unit
        ) {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener { result ->

                    val uid = result.user?.uid ?: return@addOnSuccessListener

                    val usuario = Usuario(
                        uid = uid,
                        nome = nome,
                        email = email,
                        tipo = tipo
                    )

                    db.collection("usuarios")
                        .document(uid)
                        .set(usuario)
                        .addOnSuccessListener {
                            callback(true, null)
                        }
                        .addOnFailureListener { e ->
                            callback(false, e.localizedMessage)
                        }
                }
                .addOnFailureListener { e ->
                    callback(false, e.localizedMessage)
                }
        }


    }