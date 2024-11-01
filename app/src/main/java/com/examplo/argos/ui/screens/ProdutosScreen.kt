package com.examplo.argos.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.examplo.argos.data.dto.ProdutoDto
import com.examplo.argos.data.repository.ProdutoRepository
import kotlinx.coroutines.launch

@Composable
fun ProdutosScreen(navController: NavHostController, repository: ProdutoRepository) {
    val produtos = remember { mutableStateListOf<ProdutoDto>() }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = repository.getAllProdutos()
                if (response.isNotEmpty()) {
                    produtos.addAll(response)
                } else {
                    errorMessage = "Nenhum produto encontrado."
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao carregar produtos: ${e.message}"
                Log.e("ProdutosScreen", "Erro ao carregar produtos", e)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("addProduto") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Adicionar Produto")
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(produtos) { produto ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .padding(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Nome: ${produto.nome}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Descrição: ${produto.descricao}")
                        Text(text = "Quantidade: ${produto.quantidade}")
                        Text(text = "Preço: R$ ${produto.preco}")

                        // Carregar a imagem do produto usando Coil
                        Image(
                            painter = rememberAsyncImagePainter(produto.imagem),
                            contentDescription = "Imagem do Produto",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = { navController.navigate("editProdutoScreen/${produto.id}") }) {
                                Text("Editar")
                            }
                            Button(onClick = {
                                coroutineScope.launch {
                                    repository.deleteProduto(produto.id!!)
                                    produtos.remove(produto)
                                }
                            }) {
                                Text("Deletar")
                            }
                        }
                        Divider()
                    }
                }
            }
        }
    }
}
