package com.examplo.argos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examplo.argos.data.repository.ClienteRepository
import com.examplo.argos.data.repository.ProdutoRepository
import com.examplo.argos.data.storage.AsyncStorageHelper
import com.examplo.argos.ui.screens.*
import com.examplo.argos.ui.theme.ArgosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clienteRepository = ClienteRepository(this)
        val produtoRepository = ProdutoRepository(this)
        val asyncStorageHelper = AsyncStorageHelper(this)

        setContent {
            ArgosAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController, clienteRepository, produtoRepository, asyncStorageHelper)
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    clienteRepository: ClienteRepository,
    produtoRepository: ProdutoRepository,
    asyncStorageHelper: AsyncStorageHelper
) {
    NavHost(navController = navController, startDestination = "home") {

        // Defina a rota "home"
        composable("home") { HomeScreen(navController) }

        // Defina as rotas para produtos
        composable("produtos") { ProdutosScreen(navController, produtoRepository) }
        composable("addProduto") { AddProdutoScreen(navController, produtoRepository) }
        composable("editProdutoScreen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let { EditProdutoScreen(navController, produtoRepository, it) }
        }

        // Defina as rotas para clientes
        composable("clientes") { ClientesScreen(navController, clienteRepository, asyncStorageHelper) }
        composable("addCliente") { AddClienteScreen(navController, clienteRepository) }
        composable("editClienteScreen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let { EditClienteScreen(navController, clienteRepository, it) }
        }
    }
}
