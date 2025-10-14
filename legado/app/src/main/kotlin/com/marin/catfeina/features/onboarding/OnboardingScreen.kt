// =============================================================================
// Arquivo: com.marin.catfeina.features.onboarding.OnboardingScreen.kt
// Descrição: Tela de onboarding para apresentar o aplicativo ao usuário.
// =============================================================================
package com.marin.catfeina.features.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.rive.runtime.kotlin.RiveAnimationView
import com.marin.catfeina.R
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Bem-vindo ao Catfeina!",
        description = "Seu refúgio diário para um café com poesia. Explore, sinta e organize um universo literário."
    ),
    OnboardingPage(
        title = "Leia e Sinta",
        description = "Mergulhe em poesias com leitura em voz alta, sons ambientes e dicas contextuais que enriquecem a experiência."
    ),
    OnboardingPage(
        title = "Personalize Sua Jornada",
        description = "Favorite obras, crie anotações, salve atalhos e evolua seu mascote, o Catshito, enquanto explora o app."
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onOnboardingComplete: () -> Unit
) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val scope = rememberCoroutineScope()

    val onComplete = {
        viewModel.onOnboardingComplete()
        onOnboardingComplete()
    }

    Scaffold {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                OnboardingPageContent(page = onboardingPages[pageIndex])
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onComplete) {
                    Text("Pular")
                }

                Button(onClick = {
                    if (pagerState.currentPage < onboardingPages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onComplete()
                    }
                }) {
                    Text(if (pagerState.currentPage < onboardingPages.size - 1) "Avançar" else "Concluir")
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            modifier = Modifier.size(200.dp),
            factory = {
                context ->
                RiveAnimationView(context).apply {
                    setRiveResource(
                        resId = R.raw.catshito,
                        artboardName = "MAIN",
                        autoplay = true
                    )
                }
            },
            update = {
                it.contentDescription = "Mascote Catshito"
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
