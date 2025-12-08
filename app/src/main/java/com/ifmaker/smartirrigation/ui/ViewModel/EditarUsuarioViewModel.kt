package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository

class EditarUsuarioViewModel: ViewModel() {
    private val repositoryUsuario = UsuarioRepository()
    private val _resultado = MutableLiveData<Pair<Boolean, String>>()
    var resultado = _resultado

    fun editarPerfil(nome: String, senha: String, confirmar: String) {

        if (senha.isNotEmpty()) {
            if (senha != confirmar) {
                _resultado.value = Pair(false, "As senhas não conferem.")
                return
            }
            if (senha.length < 6) {
                _resultado.value = Pair(false, "A senha deve ter pelo menos 6 caracteres.")
                return
            }
        }

        repositoryUsuario.atualizarPerfil(nome, senha) { sucesso, mensagem ->
            _resultado.value = Pair(sucesso, mensagem)
        }
    }

}