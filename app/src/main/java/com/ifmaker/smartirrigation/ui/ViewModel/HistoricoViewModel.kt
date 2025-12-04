package com.ifmaker.smartirrigation.ui.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.Historico
import com.ifmaker.smartirrigation.data.Repository.HistoricoRepository
import kotlinx.coroutines.launch

class HistoricoViewModel: ViewModel() {
    private val repository = HistoricoRepository()
    val listHistorico = MutableLiveData<List<Historico>>()

    init {
        carregar()
    }
    fun carregar() {
        viewModelScope.launch {
            try {
                val historico = repository.getHistorico()
                listHistorico.value = historico

            } catch (e: Exception) {
                Log.e("Historico", "Erro ao buscar histórico", e)
            }
        }
    }
}