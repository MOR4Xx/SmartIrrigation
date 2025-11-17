package com.ifmaker.smartirrigation.data.Model

data class UltimaIrrigacao(
    val data: String,
    val hora: String,
    val tempo: String,
    val volume_por_planta: String,
    val volume_total: String,
    val umidade_atual: String
)

