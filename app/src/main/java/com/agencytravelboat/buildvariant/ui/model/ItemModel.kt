package com.agencytravelboat.buildvariant.ui.model


data class ItemModel(
    val codigoInventario: Int,
    val descricao: String,
    val prazoAtendimento: String,
    val codigoConferencia: String,
    val listaBadges: List<BadgeModel>
)