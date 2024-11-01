package com.examplo.argos.data.network

import com.examplo.argos.data.dto.ClienteDto
import com.examplo.argos.data.dto.ProdutoDto
import retrofit2.http.*

interface ArgosApi {
    @GET("/api/clientes")
    suspend fun getClientes(): List<ClienteDto>

    @GET("/api/clientes/{id}")
    suspend fun getClienteById(@Path("id") id: Long): ClienteDto

    @POST("/api/clientes")
    suspend fun createCliente(@Body cliente: ClienteDto)

    @PUT("/api/clientes/{id}")
    suspend fun updateCliente(@Path("id") id: Long, @Body cliente: ClienteDto): ClienteDto

    @DELETE("/api/clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Long)

    // Endpoints de Produtos
    @GET("/api/produtos")
    suspend fun getProdutos(): List<ProdutoDto>  // Deve retornar List<ProdutoDto>

    @GET("/api/produtos/{id}")
    suspend fun getProdutoById(@Path("id") id: Long): ProdutoDto

    @POST("/api/produtos")
    suspend fun createProduto(@Body produto: ProdutoDto): ProdutoDto

    @PUT("/api/produtos/{id}")
    suspend fun updateProduto(@Path("id") id: Long, @Body produto: ProdutoDto): ProdutoDto

    @DELETE("/api/produtos/{id}")
    suspend fun deleteProduto(@Path("id") id: Long)
}
