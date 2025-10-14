// =============================================================================
// Arquivo: webApp.build.gradle.kts
// Descrição: Configuração de build para o módulo :webApp.
// =============================================================================
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))
            implementation(compose.ui)
        }
    }
}
