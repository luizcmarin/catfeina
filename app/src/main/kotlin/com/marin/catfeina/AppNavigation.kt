/*
 * Arquivo: com.marin.catfeina.AppNavigation.kt
 * Descrição: Contém a estrutura principal da UI da aplicação (MainAppScreen),
 *            incluindo Scaffold, navegação, e as definições de rotas.
 */
package com.marin.catfeina

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marin.catfeina.core.Icones
import com.marin.catfeina.core.data.repository.PoesiaRepository
import com.marin.catfeina.features.atelier.AtelierScreen
import com.marin.catfeina.features.informativo.InformativoScreen
import com.marin.catfeina.features.personagens.PersonagemDetalhesScreen
import com.marin.catfeina.features.personagens.PersonagensScreen
import com.marin.catfeina.features.poesias.PoesiaDetalhesScreen
import com.marin.catfeina.features.poesias.PoesiaListaScreen
import com.marin.catfeina.features.poesias.PoesiaListaViewModel
import com.marin.catfeina.features.temas.TemasScreen
import com.marin.catfeina.features.temas.TemasViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


// 1. DEFINIÇÃO DAS ROTAS E ITENS DE MENU
object AppDestinationsArgs {
    const val POESIA_ID_ARG = "poesiaId"
    const val PERSONAGEM_ID_ARG = "personagemId"
    const val INFORMATIVO_CHAVE_ARG = "informativoChave" // <-- ADICIONADO
}

object AppDestinations {
    const val ATELIER_ROUTE = "atelier"
    const val INICIO_ROUTE = "inicio"
    const val PERSONAGEM_ROUTE = "personagem" // ROTA DE PERSONAGEM CORRIGIDA
    const val PERSONAGEM_DETAIL_ROUTE = "personagem_detail"
    const val PERSONAGEM_DETAIL_WITH_ARG_ROUTE =
        "$PERSONAGEM_DETAIL_ROUTE/{${AppDestinationsArgs.PERSONAGEM_ID_ARG}}"
    const val POESIAS_ROUTE = "poesias"
    const val POESIA_DETAIL_ROUTE = "poesia_detail"
    const val POESIA_DETAIL_WITH_ARG_ROUTE =
        "$POESIA_DETAIL_ROUTE/{${AppDestinationsArgs.POESIA_ID_ARG}}"
    const val PREFERENCIAS_ROUTE = "preferencias"
    const val TEMAS_ROUTE = "temas"

    const val INFORMATIVO_DETAIL_ROUTE = "informativo_detail" // <-- ADICIONADO
    const val INFORMATIVO_DETAIL_WITH_ARG_ROUTE = // <-- ADICIONADO
        "$INFORMATIVO_DETAIL_ROUTE/{${AppDestinationsArgs.INFORMATIVO_CHAVE_ARG}}"
}

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

    val navigateTo: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationItems = listOf(
        NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
        NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
        NavMenuItem(AppDestinations.ATELIER_ROUTE, R.string.menu_atelier, Icones.Atelier),
        NavMenuItem(
            AppDestinations.PERSONAGEM_ROUTE,
            R.string.menu_personagem,
            Icones.Personagem
        ), // ROTA CORRIGIDA
        NavMenuItem(
            AppDestinations.PREFERENCIAS_ROUTE,
            R.string.menu_preferencias,
            Icones.Preferencias
        ),
        NavMenuItem(AppDestinations.TEMAS_ROUTE, R.string.menu_temas, Icones.Temas),
    )

    val bottomBarItems = listOf(
        navigationItems.find { it.route == AppDestinations.INICIO_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.POESIAS_ROUTE }!!,
        navigationItems.find { it.route == AppDestinations.TEMAS_ROUTE }!!,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                onNavigateToInformativo = { chave ->
                    navController.navigate("${AppDestinations.INFORMATIVO_DETAIL_ROUTE}/$chave")
                    scope.launch { drawerState.close() }
                },
                onNavigateToMain = { route ->
                    navigateTo(route)
                    scope.launch { drawerState.close() }
                },
                currentRoute = currentRoute
            )
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
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
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

// NOVO COMPOSABLE PARA O CONTEÚDO DO DRAWER
@Composable
private fun AppDrawerContent(
    viewModel: DrawerContentViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }, null),
    onNavigateToInformativo: (String) -> Unit,
    onNavigateToMain: (String) -> Unit,
    currentRoute: String?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    // Itens de navegação principal que aparecem no topo
    val navigationItems = listOf(
        NavMenuItem(AppDestinations.INICIO_ROUTE, R.string.menu_inicio, Icones.Inicio),
        NavMenuItem(AppDestinations.POESIAS_ROUTE, R.string.menu_poesias, Icones.Poesia),
        NavMenuItem(AppDestinations.ATELIER_ROUTE, R.string.menu_atelier, Icones.Atelier),
        NavMenuItem(AppDestinations.PERSONAGEM_ROUTE, R.string.menu_personagem, Icones.Personagem),
        NavMenuItem(AppDestinations.TEMAS_ROUTE, R.string.menu_temas, Icones.Temas),
    )

    ModalDrawerSheet {
        LazyColumn {
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(16.dp))
            }

            // Itens de navegação principal
            items(navigationItems.size) { index ->
                val item = navigationItems[index]
                NavigationDrawerItem(
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(stringResource(item.labelResId)) },
                    selected = currentRoute == item.route,
                    onClick = { onNavigateToMain(item.route) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                InfoDrawerItem(label = "PRIVACIDADE", value = "Algumas informações essenciais precisam ser transferidas entre nós e o seu dispositivo para que o aplicativo funcione corretamente. Nenhuma dessas informações vai ser vendida ou usada para fazer propaganda. Para saber mais, por favor, leia Como o Catfeina usa suas informações.")
            }

//            item {
//                SwitchableDrawerItem(
//                    title = "Enviar informações de diagnóstico",
//                    subtitle = "Ajude a melhorar o app enviando relatórios de erro.",
//                    checked = uiState.enviarDiagnostico,
//                    onCheckedChange = {
//                        scope.launch { viewModel.setEnviarDiagnostico(it) }
//                    }
//                )
//            }
//            item {
//                SwitchableDrawerItem(
//                    title = "Enviar informações de uso",
//                    subtitle = "Ajude a melhorar a aparência e desempenho.",
//                    checked = uiState.enviarUso,
//                    onCheckedChange = {
//                        scope.launch { viewModel.setEnviarUso(it) }
//                    }
//                )
//            }


            item {
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                DrawerSectionHeader("LEGAL")
            }

            item {
                ClickableDrawerItem(
                    text = "Termos de uso",
                    onClick = { onNavigateToInformativo("termos-de-uso") }
                )
            }
            item {
                ClickableDrawerItem(
                    text = "Política de privacidade",
                    onClick = { onNavigateToInformativo("politica-de-privacidade") }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(16.dp))
                DrawerSectionHeader("SOBRE")
            }

            item {
                InfoDrawerItem(label = "Versão", value = uiState.versaoApp)
            }
            item {
                InfoDrawerItem(label = "Última poesia adicionada", value = uiState.dataUltimaPoesia)
            }
        }
    }
}

// Pequenos Composables de ajuda para o Drawer
@Composable
private fun DrawerSectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun ClickableDrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun InfoDrawerItem(label: String, value: String) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SwitchableDrawerItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(16.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}


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

        // A tela de poesias usa a ViewModel da LISTA
        composable(AppDestinations.POESIAS_ROUTE) {
            val viewModel: PoesiaListaViewModel = hiltViewModel(
                checkNotNull(
                    LocalViewModelStoreOwner.current
                ) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }, null
            )
            PoesiaListaScreen(
                viewModel = viewModel,
                onPoesiaClick = { poesiaId ->
                    navController.navigate("${AppDestinations.POESIA_DETAIL_ROUTE}/$poesiaId")
                })
        }

        // A tela de detalhes usa a ViewModel de DETALHES
        composable(
            route = AppDestinations.POESIA_DETAIL_WITH_ARG_ROUTE,
            arguments = listOf(
                navArgument(AppDestinationsArgs.POESIA_ID_ARG) { type = NavType.LongType }
            )
        ) {
            PoesiaDetalhesScreen(
                navController = navController
            )
        }

        composable(AppDestinations.ATELIER_ROUTE) { AtelierScreen() }

        // --- ROTAS DE PERSONAGENS (ATUALIZADO) ---
        composable(AppDestinations.PERSONAGEM_ROUTE) { // ROTA CORRIGIDA
            PersonagensScreen(
                onPersonagemClick = { personagemId ->
                    navController.navigate("${AppDestinations.PERSONAGEM_DETAIL_ROUTE}/$personagemId")
                }
            )
        }
        composable(
            route = AppDestinations.PERSONAGEM_DETAIL_WITH_ARG_ROUTE,
            arguments = listOf(
                navArgument(AppDestinationsArgs.PERSONAGEM_ID_ARG) { type = NavType.LongType }
            )
        ) {
            PersonagemDetalhesScreen(navController = navController)
        }


        // Corrigido para injetar a ViewModel de temas corretamente
        composable(AppDestinations.TEMAS_ROUTE) {
            val viewModel: TemasViewModel =
                hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }, null)
            TemasScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- NOVA ROTA PARA INFORMATIVOS ---
        composable(
            route = AppDestinations.INFORMATIVO_DETAIL_WITH_ARG_ROUTE,
            arguments = listOf(
                navArgument(AppDestinationsArgs.INFORMATIVO_CHAVE_ARG) { type = NavType.StringType }
            )
        ) {
            InformativoScreen(navController = navController)
        }
    }
}

// NOVA VIEWMODEL PARA O DRAWER
data class DrawerUiState(
    val versaoApp: String = "",
    val dataUltimaPoesia: String = "N/D",
//    val enviarDiagnostico: Boolean = true,
//    val enviarUso: Boolean = true
)

@HiltViewModel
class DrawerContentViewModel @Inject constructor(
    poesiaRepository: PoesiaRepository,
//    private val userPreferencesRepository: UserPreferencesRepository,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val versao: String = try {
        val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo)
        "${pInfo.versionName} ($longVersionCode)"
    } catch (e: PackageManager.NameNotFoundException) {
        "N/D"
    }

    private val dataUltimaPoesia: StateFlow<String> = poesiaRepository.getUltimaPoesiaAdicionada()
        .map { poesia ->
            if (poesia != null) {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                sdf.format(Date(poesia.dataCriacao))
            } else {
                "Nenhuma poesia encontrada"
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "Carregando..."
        )

    // CORREÇÃO 2 e 3: Uso correto da função combine com tipos explícitos
    val uiState: StateFlow<DrawerUiState> = dataUltimaPoesia
        .map { dataPoesia ->
            DrawerUiState(
                versaoApp = versao,
                dataUltimaPoesia = dataPoesia
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DrawerUiState(versaoApp = versao)
        )
//
//    suspend fun setEnviarDiagnostico(ativado: Boolean) {
//        userPreferencesRepository.setEnviarDiagnostico(ativado)
//    }
//
//    suspend fun setEnviarUso(ativado: Boolean) {
//        userPreferencesRepository.setEnviarUso(ativado)
//    }
}
