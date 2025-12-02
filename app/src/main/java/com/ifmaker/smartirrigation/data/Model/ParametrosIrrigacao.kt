package com.ifmaker.smartirrigation.data.Model

data class ParametrosIrrigacao(
    val kc: String = "",
    val espacamento_linhas_m: String = "",
    val espacamento_plantas_m: String = "",
    val limiar_umidade_minima: String = "",
    val num_gotejadores_planta: String = "",
    val numero_total_plantas: String = "",
    val vazao_gotejador_lph: String = ""
)