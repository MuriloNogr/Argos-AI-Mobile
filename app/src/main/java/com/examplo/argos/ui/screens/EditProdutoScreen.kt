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
fun EditProdutoScreen(navController: NavHostController, repository: ProdutoRepository, produtoId: Long) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var imagem by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val produto = repository.getProdutoById(produtoId)
            nome = produto.nome
            descricao = produto.descricao
            quantidade = produto.quantidade.toString()
            preco = produto.preco.toString()
            imagem = produto.imagem
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
                    val produtoDto = ProdutoDto(
                        id = produtoId,
                        nome = nome,
                        descricao = descricao,
                        quantidade = quantidade.toIntOrNull() ?: 0,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        imagem = imagem
                    )
                    repository.updateProduto(produtoId, produtoDto)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Alterações")
        }
    }
}
