package com.ifmaker.smartirrigation.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.OptionMenu
import com.ifmaker.smartirrigation.data.Repository.ConfigRepository
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {

    private val repository = ConfigRepository()
    private val repositoryUsuario = UsuarioRepository()
    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude
    private val _permissao = MutableLiveData<String>()
    val permissao: LiveData<String> = _permissao
    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome


    fun getLatitude() {
        viewModelScope.launch {
            _latitude.value = repository.getLatitude()
        }
    }

    fun alterarLatitude(latitude: Double) {
        viewModelScope.launch {
            repository.setLatitude(latitude, callback = { ok ->
                _latitude.value = latitude
            })
        }
    }

    fun getNome(){
        repositoryUsuario.getNomeUsuario { nome ->
            _nome.value = nome
            Log.d("NOME", nome.toString())
        }
    }

    fun getPermissao() {
        repositoryUsuario.getPermissao { perm ->
            _permissao.value = perm
            Log.d("PERMISSAO", perm.toString())
        }
    }
}