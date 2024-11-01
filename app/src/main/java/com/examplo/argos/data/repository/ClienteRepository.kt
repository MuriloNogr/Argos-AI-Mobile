package com.examplo.argos.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.examplo.argos.data.dto.ClienteDto
import com.examplo.argos.data.models.Cliente
import com.examplo.argos.data.network.ArgosApi
import com.examplo.argos.data.network.UnsafeRetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.clienteDataStore: DataStore<Preferences> by preferencesDataStore(name = "cliente_store")

class ClienteRepository(private val context: Context) {

    private val dataStore = context.clienteDataStore
    private val api = UnsafeRetrofitInstance.createUnsafeRetrofit().create(ArgosApi::class.java)

    companion object {
        private val CLIENTES_KEY = stringPreferencesKey("clientes")
    }

    suspend fun saveClientesLocally(clientes: List<Cliente>) {
        val json = Gson().toJson(clientes)
        dataStore.edit { preferences ->
            preferences[CLIENTES_KEY] = json
        }
    }

    fun getClientesLocally(): Flow<List<Cliente>> {
        return dataStore.data.map { preferences ->
            val json = preferences[CLIENTES_KEY] ?: return@map emptyList()
            Gson().fromJson(json, Array<Cliente>::class.java).toList()
        }
    }

    suspend fun getAllClientes(): List<ClienteDto> {
        return withContext(Dispatchers.IO) {
            api.getClientes()
        }
    }

    suspend fun getClienteById(id: Long): ClienteDto {
        return api.getClienteById(id)
    }

    suspend fun createCliente(cliente: ClienteDto) {
        withContext(Dispatchers.IO) {
            api.createCliente(cliente)
        }
    }

    suspend fun updateCliente(id: Long, clienteDto: ClienteDto): ClienteDto {
        return api.updateCliente(id, clienteDto)
    }

    suspend fun deleteCliente(id: Long) {
        api.deleteCliente(id)
    }
}
