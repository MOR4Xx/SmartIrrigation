package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository

class CadastroViewModel : ViewModel() {

    private val repo = UsuarioRepository()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

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

        loading.value = true

        repo.cadastrarUsuario(nome, email, senha, permissao) { ok, erro ->
            loading.value = false
            if (ok) {
                success.value = true
            } else {
                errorMessage.value = erro ?: "Erro desconhecido"
            }
        }
    }
}
