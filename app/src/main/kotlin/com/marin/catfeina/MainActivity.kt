/*
 * Arquivo: com.marin.catfeina.MainActivity.kt
 * Descrição: Ponto de entrada da aplicação. Responsável por configurar o tema
 *            e chamar a tela principal da UI.
 */
package com.marin.catfeina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marin.catfeina.core.theme.CatfeinaTheme
import com.marin.catfeina.core.theme.GerenciadorTemas
import com.marin.catfeina.ui.theme.CatfeinaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gerenciadorTemas: GerenciadorTemas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatfeinaTheme(gerenciadorTemas = gerenciadorTemas) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama a tela principal, que agora vive em AppNavigation.kt
                    MainAppScreen()
                }
            }
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
