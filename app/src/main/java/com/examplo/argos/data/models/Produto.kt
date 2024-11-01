package com.examplo.argos.data.models

data class Produto(
    val id: Long,
    val nome: String,
    val descricao: String,
    val quantidade: Int,
    val preco: Double,
    val imagem: String
)
