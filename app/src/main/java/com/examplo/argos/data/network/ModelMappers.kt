package com.examplo.argos.data.network

import com.examplo.argos.data.dto.ClienteDto
import com.examplo.argos.data.models.Cliente
import com.examplo.argos.data.dto.ProdutoDto
import com.examplo.argos.data.models.Produto

fun ClienteDto.toModel(): Cliente {
    return Cliente(
        id_cliente = this.id_cliente ?: 0L,
        nome = this.nome,
        idade = this.idade,
        cpf = this.cpf,
        email = this.email,
        celular = this.celular
    )
}

fun ProdutoDto.toModel(): Produto {
    return Produto(
        id = this.id ?: 0L,
        nome = this.nome,
        descricao = this.descricao,
        quantidade = this.quantidade,
        preco = this.preco,
        imagem = this.imagem
    )
}
