package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.ParametrosIrrigacao
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import com.ifmaker.smartirrigation.data.Repository.ConfigRepository
import com.ifmaker.smartirrigation.data.Repository.UltimaIrrigacaoRepository
import kotlinx.coroutines.launch

class IrrigacaoViewModel : ViewModel() {

    private val repository = UltimaIrrigacaoRepository()
    private val repositoryConfig = ConfigRepository()
    private val _ultimaIrrigacao = MutableLiveData<UltimaIrrigacao>()
    val ultimaIrrigacao: LiveData<UltimaIrrigacao> = _ultimaIrrigacao
    private val _parametrosIrrigacao = MutableLiveData<ParametrosIrrigacao>()
    val parametrosIrrigacao: LiveData<ParametrosIrrigacao> = _parametrosIrrigacao


    fun getUltimaIrrigacao() {
        viewModelScope.launch {
            repository.getUltimaIrrigacao { it ->
                _ultimaIrrigacao.value = it
            }
        }
    }

    fun setModoIrrigacao(modo: String) {
        viewModelScope.launch {
            repositoryConfig.setModoIrrigacao(modo)
        }
    }

    fun getParametrosIrrigacao() {
        viewModelScope.launch {
            repositoryConfig.getParametros {
                _parametrosIrrigacao.value = it
            }
        }
    }

    fun setQuantidadeAguaManual(quantidade: Int) {
        viewModelScope.launch {
            repositoryConfig.setQuantidadeAguaManual(quantidade)
        }
    }

    fun setAutorizacaoIrrigacaoAutomatica(autorizacao: Boolean) {
        viewModelScope.launch {
            repositoryConfig.setAutorizacaoIrrigacaoAutomatica(autorizacao)
        }
    }
}

