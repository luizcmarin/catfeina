/*
 * Arquivo: com.marin.catfeina.AppNavigation.kt
 * Descrição: Contém a estrutura principal da UI da aplicação (MainAppScreen),
 *            incluindo Scaffold, navegação, e as definições de rotas.
 */
package com.marin.catfeina

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marin.catfeina.core.Icones
import com.marin.catfeina.features.atelier.ATELIER_EDIT_WITH_ARG_ROUTE
import com.marin.catfeina.features.atelier.ATELIER_ITEM_ID_ARG
import com.marin.catfeina.features.atelier.AtelierEditScreen
import com.marin.catfeina.features.atelier.AtelierScreen
import com.marin.catfeina.features.historico.HistoricoScreen
import com.marin.catfeina.features.informativo.InformativoScreen
import com.marin.catfeina.features.inicio.InicioScreen
import com.marin.catfeina.features.marcador.MarcadorScreen
import com.marin.catfeina.features.mascote.ConquistasScreen
import com.marin.catfeina.features.onboarding.OnboardingScreen
import com.marin.catfeina.features.personagens.PersonagemDetalhesScreen
import com.marin.catfeina.features.personagens.PersonagensScreen
import com.marin.catfeina.features.poesias.PoesiaDetalhesScreen
import com.marin.catfeina.features.poesias.PoesiaListaScreen
import com.marin.catfeina.features.search.SearchScreen
import com.marin.catfeina.features.temas.TemasScreen
import com.marin.catfeina.features.vozes.VozesScreen
import kotlinx.coroutines.launch

object AppDestinationsArgs {
    const val POESIA_ID_ARG = "poesiaId"
    const val PERSONAGEM_ID_ARG = "personagemId"
    const val INFORMATIVO_CHAVE_ARG = "informativoChave"
}

object AppDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val ATELIER_ROUTE = "atelier"
    const val CONQUISTAS_ROUTE = "conquistas"
    const val INICIO_ROUTE = "inicio"
    const val PERSONAGEM_ROUTE = "personagem"
    const val PERSONAGEM_DETALHES_ROUTE = "personagem_detail"
    const val PERSONAGEM_DETALHES_WITH_ARG_ROUTE = "$PERSONAGEM_DETALHES_ROUTE/{${AppDestinationsArgs.PERSONAGEM_ID_ARG}}"
    const val POESIAS_ROUTE = "poesias"
    const val POESIA_DETALHES_ROUTE = "poesia_detail"
    const val POESIA_DETALHES_WITH_ARG_ROUTE = "$POESIA_DETALHES_ROUTE/{${AppDestinationsArgs.POESIA_ID_ARG}}"
    const val VOZES_ROUTE = "vozes"
    const val TEMAS_ROUTE = "temas"
    const val HISTORICO_ROUTE = "historico"
    const val MARCADOR_ROUTE = "marcador"
    const val SEARCH_ROUTE = "search"

    const val INFORMATIVO_DETAIL_ROUTE = "informativo_detail"
    const val INFORMATIVO_DETAIL_WITH_ARG_ROUTE = "$INFORMATIVO_DETAIL_ROUTE/{${AppDestinationsArgs.INFORMATIVO_CHAVE_ARG}}"
}

private val listaCompletaDeNavegacao = listOf(
    NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
    NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
    NavMenuItem(AppDestinations.ATELIER_ROUTE, R.string.menu_atelier, Icones.Atelier),
    NavMenuItem(AppDestinations.PERSONAGEM_ROUTE, R.string.menu_personagem, Icones.Personagem),
    NavMenuItem(AppDestinations.CONQUISTAS_ROUTE, R.string.menu_conquistas, Icones.Conquista),
    NavMenuItem(AppDestinations.MARCADOR_ROUTE, R.string.menu_marcadores, Icones.MarcadorCheio),
    NavMenuItem(AppDestinations.HISTORICO_ROUTE, R.string.menu_historico, Icones.TresPontosVertical),
    NavMenuItem(AppDestinations.VOZES_ROUTE, R.string.menu_vozes, Icones.Vozes),
    NavMenuItem(AppDestinations.TEMAS_ROUTE, R.string.menu_temas, Icones.Temas),
)

private val listaCompletaDeNavegacaoBottomBar = listOf(
    NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
    NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
    NavMenuItem(AppDestinations.MARCADOR_ROUTE, R.string.menu_marcadores, Icones.MarcadorCheio),
    NavMenuItem(AppDestinations.ATELIER_ROUTE, R.string.menu_atelier, Icones.Atelier)
)

data class NavMenuItem(val route: String, val labelResId: Int, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val mainUiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    if (mainUiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Onboarding temporariamente desabilitado para testes, forçando a rota inicial.
        val startDestination = AppDestinations.INICIO_ROUTE 
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigateTo: (String) -> Unit = {
            navController.navigate(it) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    listaCompletaDeNavegacao.forEach { screen ->
                        NavigationDrawerItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(id = screen.labelResId)) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                navigateTo(screen.route)
                            }
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = { /* ... */ },
                bottomBar = {
                    NavigationBar {
                        listaCompletaDeNavegacaoBottomBar.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = null) },
                                label = { Text(stringResource(id = screen.labelResId)) },
                                selected = currentRoute == screen.route,
                                onClick = { navigateTo(screen.route) }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                AppNavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.ONBOARDING_ROUTE) {
            OnboardingScreen(onOnboardingComplete = { 
                navController.navigate(AppDestinations.INICIO_ROUTE) {
                    popUpTo(AppDestinations.ONBOARDING_ROUTE) { inclusive = true }
                }
            })
        }
        composable(AppDestinations.INICIO_ROUTE) { InicioScreen(navController = navController) }
        composable(AppDestinations.POESIAS_ROUTE) { PoesiaListaScreen { navController.navigate("${AppDestinations.POESIA_DETALHES_ROUTE}/$it") } }
        composable(
            route = AppDestinations.POESIA_DETALHES_WITH_ARG_ROUTE,
            arguments = listOf(navArgument(AppDestinationsArgs.POESIA_ID_ARG) { type = NavType.LongType })
        ) { PoesiaDetalhesScreen(navController = navController) }
        composable(AppDestinations.ATELIER_ROUTE) { AtelierScreen(navController = navController) }
        composable(
            route = ATELIER_EDIT_WITH_ARG_ROUTE,
            arguments = listOf(navArgument(ATELIER_ITEM_ID_ARG) { 
                type = NavType.LongType 
                defaultValue = 0L
            })
        ) { AtelierEditScreen(navController = navController) }
        composable(AppDestinations.PERSONAGEM_ROUTE) { PersonagensScreen { navController.navigate("${AppDestinations.PERSONAGEM_DETALHES_ROUTE}/$it") } }
        composable(
            route = AppDestinations.PERSONAGEM_DETALHES_WITH_ARG_ROUTE,
            arguments = listOf(navArgument(AppDestinationsArgs.PERSONAGEM_ID_ARG) { type = NavType.LongType })
        ) { PersonagemDetalhesScreen(navController = navController) }
        composable(AppDestinations.TEMAS_ROUTE) {
            TemasScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(AppDestinations.INFORMATIVO_DETAIL_WITH_ARG_ROUTE) { InformativoScreen(navController = navController) }
        composable(AppDestinations.VOZES_ROUTE) { VozesScreen() }
        composable(AppDestinations.HISTORICO_ROUTE) { HistoricoScreen(navController = navController) }
        composable(AppDestinations.MARCADOR_ROUTE) { MarcadorScreen(navController = navController) }
        composable(AppDestinations.SEARCH_ROUTE) { SearchScreen(navController = navController) }
        composable(AppDestinations.CONQUISTAS_ROUTE) { ConquistasScreen(navController = navController) }
    }
}
