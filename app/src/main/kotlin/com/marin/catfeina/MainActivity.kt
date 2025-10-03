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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marin.catfeina.core.utils.Icones
import com.marin.catfeina.ui.theme.CatfeinaTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marin.catfeina.core.data.UserPreferencesRepository
import com.marin.catfeina.ui.preferencias.PreferenciasScreen
import com.marin.catfeina.ui.preferencias.PreferenciasViewModel


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatfeinaTheme {
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
fun MainAppScreen(
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Observa a rota atual para UI (TopAppBar título, item selecionado)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Defina seus itens de navegação aqui, usando as rotas de AppDestinations
    // e os recursos de string/ícones correspondentes.
    val navigationItems = listOf(
        NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
        NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
        NavMenuItem(AppDestinations.PERSONAGEM_ROUTE, R.string.menu_personagem, Icones.Personagem),
        NavMenuItem(
            AppDestinations.PREFERENCIAS_ROUTE,
            R.string.menu_preferencias,
            Icones.Preferencias
        )
        // Adicione outros itens que estarão no Drawer
    )

    // Itens que aparecerão especificamente na BottomBar (um subconjunto ou os mesmos)
    val bottomBarItems = listOf(
        navigationItems.find { it.route == AppDestinations.INICIO_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.POESIAS_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.PREFERENCIAS_ROUTE }!!
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
                // Condicionalmente mostrar BottomAppBar (ex: apenas em telas de nível superior)
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
//            InicioScreen() // Sua tela de AppNavigation.kt
        }
        composable(AppDestinations.POESIAS_ROUTE) {
//            PoesiasScreen() // Sua tela de AppNavigation.kt
        }
        composable(AppDestinations.PERSONAGEM_ROUTE) {
//            PersonagemScreen() // Sua tela de AppNavigation.kt
        }


        composable(AppDestinations.PREFERENCIAS_ROUTE) {
            val context = LocalContext.current
            val userPreferencesRepository = UserPreferencesRepository(context)
            val viewModel: PreferenciasViewModel = viewModel(
                factory = PreferenciasViewModel.provideFactory(userPreferencesRepository)
            )

            // Coleta o estado do Flow para a UI
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            PreferenciasScreen(
                isDarkMode = isDarkMode,
                onDarkModeChange = { viewModel.setDarkMode(it) }
            )
        }

        // Se você tiver rotas com argumentos, defina-as aqui também. Ex:
        // composable(
        //     route = AppDestinations.Texto_DETALHE_ROUTE_TEMPLATE,
        //     arguments = listOf(navArgument(AppDestinations.Texto_ARG_CHAVE) { type = NavType.StringType })
        // ) { backStackEntry ->
        //     val chaveTexto = backStackEntry.arguments?.getString(AppDestinations.Texto_ARG_CHAVE)
        //     if (chaveTexto != null) {
        //         // DetalheTextoScreen(chaveTexto = chaveTexto) // Sua tela de detalhes
        //     } else {
        //         // Lidar com chave nula, talvez voltar ou mostrar erro
        //         Text("Erro: Chave do texto não encontrada.")
        //     }
        // }

        // Adicione outras rotas do seu AppDestinations aqui
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatfeinaTheme {
        MainAppScreen()
    }
}
