package com.ifmaker.smartirrigation.data.Model

data class OptionMenu(
    val title: String
)

object OptionMenuAdm {
    fun getMenuList(): List<OptionMenu> {
        return listOf(
            OptionMenu("Latitude do Sistema"),
            OptionMenu("Tipo de Platio"),
            OptionMenu("Adicionar Novo Usuario"),
            OptionMenu("Notificações"),
            OptionMenu("Aparencia"),
            OptionMenu("Idioma"),
            OptionMenu("Logout")
        )
    }
}

object OptionMenuUser {
    fun getMenuList(): List<OptionMenu> {
        return listOf(
            OptionMenu("Notificações"),
            OptionMenu("Aparencia"),
            OptionMenu("Idioma"),
            OptionMenu("Logout")
        )
    }
}