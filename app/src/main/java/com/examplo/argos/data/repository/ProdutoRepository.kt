package com.examplo.argos.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.examplo.argos.data.dto.ProdutoDto
import com.examplo.argos.data.models.Produto
import com.examplo.argos.data.network.ArgosApi
import com.examplo.argos.data.network.UnsafeRetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.produtoDataStore: DataStore<Preferences> by preferencesDataStore(name = "produto_store")

class ProdutoRepository(private val context: Context) {

    private val dataStore = context.produtoDataStore
    private val api = UnsafeRetrofitInstance.createUnsafeRetrofit().create(ArgosApi::class.java)

    companion object {
        private val PRODUTOS_KEY = stringPreferencesKey("produtos")
    }

    suspend fun saveProdutosLocally(produtos: List<Produto>) {
        val json = Gson().toJson(produtos)
        dataStore.edit { preferences ->
            preferences[PRODUTOS_KEY] = json
        }
    }

    fun getProdutosLocally(): Flow<List<Produto>> {
        return dataStore.data.map { preferences ->
            val json = preferences[PRODUTOS_KEY] ?: return@map emptyList()
            Gson().fromJson(json, Array<Produto>::class.java).toList()
        }
    }

    suspend fun getAllProdutos(): List<ProdutoDto> {
        return withContext(Dispatchers.IO) {
            api.getProdutos()
        }
    }

    suspend fun getProdutoById(id: Long): ProdutoDto {
        return api.getProdutoById(id)
    }

    suspend fun createProduto(produto: ProdutoDto) {
        withContext(Dispatchers.IO) {
            api.createProduto(produto)
        }
    }

    suspend fun updateProduto(id: Long, produtoDto: ProdutoDto): ProdutoDto {
        return api.updateProduto(id, produtoDto)
    }

    suspend fun deleteProduto(id: Long) {
        api.deleteProduto(id)
    }
}
