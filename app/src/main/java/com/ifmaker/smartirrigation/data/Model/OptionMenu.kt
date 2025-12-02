package com.ifmaker.smartirrigation.data.Model

data class OptionMenu(
    val title: String
)

object OptionMenuGet {
    fun getMenuList(): List<OptionMenu> {
        return listOf(
            OptionMenu("Latitude do Sistema"),
            OptionMenu("Tipo de Plantio"),
            OptionMenu("Adicionar Novo Usuario"),
            OptionMenu("Logout")
        )
    }
}
