package com.examplo.argos.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import com.examplo.argos.data.models.Produto
import com.examplo.argos.data.network.RetrofitInstance
import com.examplo.argos.data.network.toModel
import kotlinx.coroutines.launch

@Composable
fun DetalheProdutoScreen(produtoId: Long, navController: NavHostController) {
    var produto by remember { mutableStateOf<Produto?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(produtoId) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getProdutoById(produtoId)
                produto = response.toModel()
            } catch (e: Exception) {
            }
        }
    }

    produto?.let {
        Text(text = "Detalhes do Produto: Nome: ${it.nome}, Preço: ${it.preco}")
    }
}
