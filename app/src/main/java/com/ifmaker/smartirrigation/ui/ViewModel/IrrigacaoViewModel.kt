package com.ifmaker.smartirrigation.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifmaker.smartirrigation.data.Model.CoeficienteCultura
import com.ifmaker.smartirrigation.data.Model.ParametrosIrrigacao
import com.ifmaker.smartirrigation.data.Model.UltimaIrrigacao
import com.ifmaker.smartirrigation.data.Repository.ConfigRepository
import com.ifmaker.smartirrigation.data.Repository.UltimaIrrigacaoRepository
import kotlinx.coroutines.launch

class IrrigacaoViewModel : ViewModel() {

    private lateinit var cultura: String
    private lateinit var fase: String
    private lateinit var coeficienteCultura: CoeficienteCultura

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

    fun setParametrosIrrigacao(
        novoEspacoLinha: String,
        novoEspacoPlanta: String,
        novaUmidadeMinima: String,
        novoGotejadoresPlanta: String,
        novaQuantidadePlanta: String,
        novaVazaoGotejador: String
    ) {
        repositoryConfig.getDadosCultura { culturaSistema, faseRecuperada ->
            this.cultura = culturaSistema
            this.fase = faseRecuperada

            repositoryConfig.getKc(culturaSistema) { dadosKc ->

                if (dadosKc != null) {
                    val kcSelecionado = when (faseRecuperada.toString()) {
                        "Fase 1" -> dadosKc.kc1
                        "Fase 2" -> dadosKc.kc2
                        "Fase 3" -> dadosKc.kc3
                        "Fase 4" -> dadosKc.kc4
                        else -> dadosKc.kc1
                    }

                    val parametrosAtualizados = ParametrosIrrigacao(
                        kc = kcSelecionado,
                        espacamento_linhas_m = novoEspacoLinha,
                        espacamento_plantas_m = novoEspacoPlanta,
                        limiar_umidade_minima = novaUmidadeMinima,
                        num_gotejadores_planta = novoGotejadoresPlanta,
                        numero_total_plantas = novaQuantidadePlanta,
                        vazao_gotejador_lph = novaVazaoGotejador
                    )

                    viewModelScope.launch {
                        repositoryConfig.setParametros(parametrosAtualizados)
                    }
                }
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

