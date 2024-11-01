package com.examplo.argos.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.examplo.argos.data.dto.ClienteDto
import com.examplo.argos.data.repository.ClienteRepository
import com.examplo.argos.data.storage.AsyncStorageHelper
import kotlinx.coroutines.launch

@Composable
fun ClientesScreen(
    navController: NavHostController,
    repository: ClienteRepository,
    asyncStorageHelper: AsyncStorageHelper
) {
    val clientes = remember { mutableStateListOf<ClienteDto>() }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = repository.getAllClientes()
                if (response.isNotEmpty()) {
                    clientes.addAll(response)
                } else {
                    errorMessage = "Nenhum cliente encontrado."
                }

                val storedName = asyncStorageHelper.getString("last_client_name")
                Log.d("ClientesScreen", "Último nome armazenado: $storedName")

            } catch (e: Exception) {
                errorMessage = "Erro ao carregar clientes: ${e.message}"
                Log.e("ClientesScreen", "Erro ao carregar clientes", e)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("addCliente") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Adicionar Cliente")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(clientes) { cliente ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Text(text = "Nome: ${cliente.nome}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Idade: ${cliente.idade}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "CPF: ${cliente.cpf}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Email: ${cliente.email}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Celular: ${cliente.celular}", style = MaterialTheme.typography.bodyMedium)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        asyncStorageHelper.saveString("last_client_name", cliente.nome)
                                    }
                                    navController.navigate("editClienteScreen/${cliente.id_cliente}")
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Editar")
                            }
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        repository.deleteCliente(cliente.id_cliente!!)
                                        clientes.remove(cliente)
                                    }
                                }
                            ) {
                                Text("Deletar")
                            }
                        }
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}
