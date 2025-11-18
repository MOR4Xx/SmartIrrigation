package com.ifmaker.smartirrigation.ui.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository

class LoginViewModel : ViewModel() {
    val repo: UsuarioRepository = UsuarioRepository()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(login: String, password: String) {
        // login logic
        val username = login
        val password = password

        repo.login(username, password, { ok, erro ->
            if (ok) {
                _loginResult.value = true
            } else {
                _loginResult.value = false
            }
        })
    }
}
