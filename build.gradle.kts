// =============================================================================
// Arquivo: build.gradle.kts (Nível de Projeto)
// Descrição: Configuração de build para o projeto Catfeina KMP. Declara os
//            plugins disponíveis para os submódulos e propriedades globais.
// =============================================================================

// Define propriedades globais que podem ser lidas por qualquer submódulo de forma
// segura e com tipagem explícita.
extra.apply {
    // Android SDKs
    set("compileSdk", 36)                // Com o que o app é construído. O nível de API que o compilador usará para verificar se há APIs disponíveis.
    set("targetSdk", 36)                 // Como o app se comporta. O nível de API para o qual o app foi otimizado.
    set("minSdk", 26)                    // Onde o app pode ser instalado. O nível de API mais antigo que o app suporta.

    // Ferramentas JVM (JDK)
    set("versionJDK", 21)                // Define a versão do Java Development Kit (JDK) que o Gradle deve usar para compilar seu código.
    set("packageName", "Catfeina")       // Nome do app :desktopApp
    set("packageVersion", "1.0.0")       // Versão para Desktop/iOS/Web
    set("versionCode", 1)                // Código numérico para Android
    set("versionName", "1.0.0")          // Nome de exibição para Android e outros
}

plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose.multiplatform).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.kmp.library).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.metro).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)
    alias(libs.plugins.buildKonfig).apply(false)
    alias(libs.plugins.composeHotReload).apply(false)
}
