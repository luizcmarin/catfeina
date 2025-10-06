/*
 * Arquivo: com.marin.catfeina.AppNavigation.kt
 * Descrição: Contém a estrutura principal da UI da aplicação (MainAppScreen),
 *            incluindo Scaffold, navegação, e as definições de rotas.
 */
package com.marin.catfeina

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marin.catfeina.core.utils.Icones
import com.marin.catfeina.features.temas.TemasScreen
import kotlinx.coroutines.launch

// 1. DEFINIÇÃO DAS ROTAS E ITENS DE MENU
object AppDestinations {
    const val INICIO_ROUTE = "inicio"
    const val POESIAS_ROUTE = "poesias"
    const val PERSONAGEM_ROUTE = "personagem"
    const val PREFERENCIAS_ROUTE = "preferencias"
    const val TEMAS_ROUTE = "temas"
}

data class NavMenuItem(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector
)

// 2. TELA PRINCIPAL QUE CONTÉM TODA A LÓGICA DA UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigateTo: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // LISTA DE ITENS DE NAVEGAÇÃO RESTAURADA
    val navigationItems = listOf(
        NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
        NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
        NavMenuItem(AppDestinations.PERSONAGEM_ROUTE, R.string.menu_personagem, Icones.Personagem),
        NavMenuItem(AppDestinations.PREFERENCIAS_ROUTE, R.string.menu_preferencias, Icones.Preferencias),
        NavMenuItem(AppDestinations.TEMAS_ROUTE, R.string.menu_temas, Icones.Temas), // <-- ITEM DE MENU RESTAURADO
    )

    // Decidi manter a barra inferior com os mesmos itens por enquanto. Você decide se o item 'Temas' entra aqui.
    val bottomBarItems = listOf(
        navigationItems.find { it.route == AppDestinations.INICIO_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.POESIAS_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.TEMAS_ROUTE }!!,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
                Spacer(Modifier.height(16.dp))

                // O Drawer agora exibirá todos os itens, incluindo "Temas"
                navigationItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(item.labelResId)) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navigateTo(item.route)
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                val topAppBarTitle = navigationItems.find { it.route == currentRoute }
                    ?.let { stringResource(it.labelResId) }
                    ?: stringResource(id = R.string.app_name)

                TopAppBar(
                    title = { Text(topAppBarTitle) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                Icones.Menu,
                                contentDescription = stringResource(id = R.string.menu_navegacao_descricao)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            bottomBar = {
                if (bottomBarItems.any { it.route == currentRoute }) {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        bottomBarItems.forEach { item ->
                            IconButton(
                                onClick = { navigateTo(item.route) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(item.labelResId),
                                    tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

// 3. O NAVHOST QUE CONTROLA AS TELAS
@Composable
private fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_ROUTE,
        modifier = modifier
    ) {
        composable(AppDestinations.INICIO_ROUTE) { Text("Tela de Início") }
        composable(AppDestinations.POESIAS_ROUTE) { Text("Tela de Poesias") }
        composable(AppDestinations.PERSONAGEM_ROUTE) { Text("Tela de Personagem") }
//        composable(AppDestinations.PREFERENCIAS_ROUTE) {
//            val viewModel: PreferenciasViewModel = hiltViewModel()
//            PreferenciasScreen(
//                viewModel = viewModel,
//                onNavigateBack = { navController.popBackStack() }
//            )
//        }
        composable(AppDestinations.TEMAS_ROUTE) {
            TemasScreen(
                viewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }, null),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
