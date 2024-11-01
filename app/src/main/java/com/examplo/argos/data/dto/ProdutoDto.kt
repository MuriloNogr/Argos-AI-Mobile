// ProdutoDto.kt
package com.examplo.argos.data.dto

import com.google.gson.annotations.SerializedName

data class ProdutoDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("descricao")
    val descricao: String,

    @SerializedName("quantidade")
    val quantidade: Int,

    @SerializedName("preco")
    val preco: Double,

    @SerializedName("imagem")
    val imagem: String,

    @SerializedName("links")
    val links: List<Link>? = null
)
