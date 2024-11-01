package com.examplo.argos.data.models

data class Cliente(
    val id_cliente: Long,
    val nome: String,
    val idade: Int,
    val cpf: String,
    val email: String,
    val celular: String
)
