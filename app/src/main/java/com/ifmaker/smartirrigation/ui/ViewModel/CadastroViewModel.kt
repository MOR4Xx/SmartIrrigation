package com.ifmaker.smartirrigation.ui.ViewModel

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.ifmaker.smartirrigation.R
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository

class CadastroViewModel : ViewModel() {

    private val repo = UsuarioRepository()
    val errorMessage = MutableLiveData<String>()
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success


    fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        confirmar: String,
        permissao: String
    ) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            errorMessage.value = "Preencha todos os campos"
            return
        }

        if (senha != confirmar) {
            errorMessage.value = "As senhas não coincidem"
            return
        }

        repo.cadastrarUsuario(nome, email, senha, permissao) { ok, erro ->
            if (ok) {
                _success.value = true
            } else {
                _success.value = false
                errorMessage.value = erro ?: "Erro desconhecido"
            }
        }
    }

}
