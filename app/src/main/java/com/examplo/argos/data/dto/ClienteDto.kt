package com.examplo.argos.data.dto

import com.google.gson.annotations.SerializedName

data class ClienteDto(
    @SerializedName("id_cliente")
    val id_cliente: Long? = null,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("idade")
    val idade: Int,

    @SerializedName("cpf")
    val cpf: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("celular")
    val celular: String,

    @SerializedName("links")
    val links: List<Link>? = null
)
