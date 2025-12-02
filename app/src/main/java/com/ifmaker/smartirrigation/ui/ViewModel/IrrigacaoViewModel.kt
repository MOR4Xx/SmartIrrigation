package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import com.ifmaker.smartirrigation.data.Repository.UltimaIrrigacaoRepository
import kotlinx.coroutines.launch

class IrrigacaoViewModel : ViewModel() {

    private val repository = UltimaIrrigacaoRepository()

    private val _ultimaIrrigacao = MutableLiveData<UltimaIrrigacao>()
    val ultimaIrrigacao: LiveData<UltimaIrrigacao> = _ultimaIrrigacao

    fun getUltimaIrrigacao() {
        viewModelScope.launch {
            repository.getUltimaIrrigacao { it ->
                _ultimaIrrigacao.value = it

            }
        }
    }


}