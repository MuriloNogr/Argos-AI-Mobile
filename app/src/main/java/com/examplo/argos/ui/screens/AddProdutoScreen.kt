package com.examplo.argos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.examplo.argos.data.dto.ProdutoDto
import com.examplo.argos.data.repository.ProdutoRepository
import kotlinx.coroutines.launch

@Composable
fun AddProdutoScreen(navController: NavHostController, repository: ProdutoRepository) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var imagem by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = { Text("Quantidade") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = imagem,
            onValueChange = { imagem = it },
            label = { Text("URL da Imagem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val produto = ProdutoDto(
                            nome = nome,
                            descricao = descricao,
                            quantidade = quantidade.toIntOrNull() ?: 0,
                            preco = preco.toDoubleOrNull() ?: 0.0,
                            imagem = imagem
                        )
                        repository.createProduto(produto)
                        navController.popBackStack()
                    } catch (e: Exception) {
                        errorMessage = "Erro ao adicionar produto. Verifique os dados e tente novamente."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Produto")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
