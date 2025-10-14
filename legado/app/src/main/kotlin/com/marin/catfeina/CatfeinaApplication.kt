/*
 * Arquivo: com.marin.catfeina.CatfeinaApplication.kt
 * Descrição: Ponto de entrada principal da aplicação, responsável pela inicialização
 *            de serviços e configuração da injeção de dependência com Hilt.
 */
package com.marin.catfeina

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatfeinaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // A lógica de inicialização (Rive, WorkManager) foi removida para testes.
    }
}
