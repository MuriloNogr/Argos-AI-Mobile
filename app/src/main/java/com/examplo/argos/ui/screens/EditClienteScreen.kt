package com.examplo.argos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.examplo.argos.data.dto.ClienteDto
import com.examplo.argos.data.repository.ClienteRepository
import kotlinx.coroutines.launch

@Composable
fun EditClienteScreen(navController: NavHostController, repository: ClienteRepository, clienteId: Long) {
    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val cliente = repository.getClienteById(clienteId)
            nome = cliente.nome
            idade = cliente.idade.toString()
            cpf = cliente.cpf
            email = cliente.email
            celular = cliente.celular
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = idade,
            onValueChange = { idade = it },
            label = { Text("Idade") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = celular,
            onValueChange = { celular = it },
            label = { Text("Celular") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val clienteDto = ClienteDto(
                        id_cliente = clienteId,
                        nome = nome,
                        idade = idade.toIntOrNull() ?: 0,
                        cpf = cpf,
                        email = email,
                        celular = celular
                    )
                    repository.updateCliente(clienteId, clienteDto)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Alterações")
        }
    }
}
