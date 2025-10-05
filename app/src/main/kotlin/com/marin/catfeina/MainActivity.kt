/*
 * Arquivo: com.marin.catfeina.MainActivity.kt
 * Arquivo inicial do projeto.
 */
package com.marin.catfeina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marin.catfeina.core.theme.CatfeinaTheme
import com.marin.catfeina.core.utils.Icones
import com.marin.catfeina.ui.theme.TemasScreen
import com.marin.catfeina.core.theme.GerenciadorTemas
import com.marin.catfeina.ui.theme.CatfeinaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var GerenciadorTemas: GerenciadorTemas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CatfeinaTheme(GerenciadorTemas = GerenciadorTemas) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                MainAppScreen()
                }
            }
        }
    }
}

// Estrutura para definir os itens de menu com rota, label e ícone
data class NavMenuItem(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
        NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
        NavMenuItem(AppDestinations.PERSONAGEM_ROUTE, R.string.menu_personagem, Icones.Personagem),
        NavMenuItem(AppDestinations.PREFERENCIAS_ROUTE, R.string.menu_preferencias, Icones.Preferencias),
                NavMenuItem(AppDestinations.TEMAS_ROUTE, R.string.menu_temas, Icones.Temas),
    )

    val bottomBarItems = listOf(
        navigationItems.find { it.route == AppDestinations.INICIO_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.POESIAS_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.PREFERENCIAS_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.TEMAS_ROUTE }!!
    )

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

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
            modifier = Modifier.fillMaxSize(),
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

@Composable
fun AppNavHost(
    navController: NavHostController, modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_ROUTE,
        modifier = modifier
    ) {
        composable(AppDestinations.INICIO_ROUTE) {
            // Placeholder
            Text("Tela de Início")
        }
        composable(AppDestinations.POESIAS_ROUTE) {
            // Placeholder
            Text("Tela de Poesias")
        }
        composable(AppDestinations.PERSONAGEM_ROUTE) {
            // Placeholder
            Text("Tela de Personagem")
        }
//        composable(AppDestinations.PREFERENCIAS_ROUTE) {
//            PreferenciasScreen(
//                viewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
//                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
//                }, null),
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatfeinaTheme {
        MainAppScreen()
    }
}
