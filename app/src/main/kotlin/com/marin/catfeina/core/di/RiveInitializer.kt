// ===================================================================================
// Arquivo: com.marin.catfeina.core.di.RiveInitializer.kt
// Descrição: Gerencia a inicialização da biblioteca Rive usando o androidx.startup.
// Propósito:
// Esta classe implementa a interface `Initializer` para garantir que a biblioteca
// Rive (`Rive.init()`) seja chamada de forma eficiente e automática na inicialização
// do aplicativo. Isso centraliza a configuração e melhora a performance de startup,
// evitando a necessidade de chamar `Rive.init()` manualmente na classe Application.
//
// A descoberta deste inicializador é feita pelo `InitializationProvider` declarado
// no AndroidManifest.xml.
// ===================================================================================
package com.marin.catfeina.core.di

import android.content.Context
import androidx.startup.Initializer
import app.rive.runtime.kotlin.core.Rive

class RiveInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        return Rive.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}