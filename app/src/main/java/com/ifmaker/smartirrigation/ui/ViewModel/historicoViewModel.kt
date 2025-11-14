package com.ifmaker.smartirrigation.ui.ViewModel

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.Historico
import com.ifmaker.smartirrigation.data.Model.HistoricoRepository
import com.ifmaker.smartirrigation.ui.Adapter.HistoricoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class historicoViewModel: ViewModel() {
    private val repo = HistoricoRepository()
    val listHistorico = MutableLiveData<List<Historico>>()
    lateinit var adapter: HistoricoAdapter

    init {
        Log.d("Arrumando saporra", "iniciando viewModel")
        carregar()
    }
    fun carregar() {
        Log.d("Arrumando saporra", "carregando dados")
        CoroutineScope(viewModelScope.coroutineContext).launch {
           Log.d("Arrumando saporra", "carregando dados 2")
            Log.d("Arrumando saporra", repo.getHistorico().toString())
        }
    }
}