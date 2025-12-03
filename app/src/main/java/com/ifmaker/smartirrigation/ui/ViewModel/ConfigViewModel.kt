package com.ifmaker.smartirrigation.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Repository.ConfigRepository
import com.ifmaker.smartirrigation.data.Repository.UsuarioRepository
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {

    private val repository = ConfigRepository()
    private val repositoryUsuario = UsuarioRepository()
    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude
    private val _permissao = MutableLiveData<String?>()
    val permissao: LiveData<String?> = _permissao
    private val _nome = MutableLiveData<String?>()
    val nome: LiveData<String?> = _nome
    private val _listCultura = MutableLiveData<List<String?>>()
    val listCultura: LiveData<List<String?>> = _listCultura
    private val _cultura = MutableLiveData<String?>()
    val cultura: LiveData<String?> = _cultura
    private val _fase = MutableLiveData<String?>()
    val fase: LiveData<String?> = _fase


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

    fun getNome() {
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

    fun getCultura() {
        repository.getCultura { cultura ->
            _listCultura.value = cultura
            Log.d("Cultura", cultura.toString())
        }
    }

    fun alterarTipoCultura(tipoCultura: String, fase: String) {
        repository.getKc(tipoCultura) { dadosKc ->
            if (dadosKc != null) {
                val kcSelecionado = when (fase) {
                    "Fase 1" -> dadosKc.kc1
                    "Fase 2" -> dadosKc.kc2
                    "Fase 3" -> dadosKc.kc3
                    "Fase 4" -> dadosKc.kc4
                    else -> dadosKc.kc1
                }

                viewModelScope.launch {
                    repository.setPlantio(tipoCultura, fase)
                    repository.setKc(kcSelecionado)

                }
            }
        }
    }

    fun getDados(){
        repository.getDadosCultura { cultura, fase ->
            _cultura.value = cultura
            _fase.value = fase
        }
    }
}
