package com.ifmaker.smartirrigation.ui.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.Historico
import com.ifmaker.smartirrigation.data.Model.HistoricoRepository
import com.ifmaker.smartirrigation.ui.Adapter.HistoricoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HistoricoViewModel: ViewModel() {
    private val repo = HistoricoRepository()
    val listHistorico = MutableLiveData<List<Historico>>()
    lateinit var adapter: HistoricoAdapter

    init {
        Log.d("Arrumando saporra", "iniciando viewModel")
        carregar()
    }
    fun carregar() {
        Log.d("Arrumando saporra", "carregando dados")

        viewModelScope.launch {
            try {
                Log.d("Arrumando saporra", "buscando dados no firebase...")

                // 1. Chamamos a função suspend (o código "espera" aqui sem travar o app)
                val historico = repo.getHistorico()

                Log.d("Arrumando saporra", "dados recebidos: $historico")

                // 2. Atualizamos o LiveData com o resultado
                listHistorico.value = historico // Dentro do main thread (viewModelScope) pode usar .value

            } catch (e: Exception) {
                Log.e("Arrumando saporra", "Erro ao buscar histórico", e)
            }
        }
    }
}