// =============================================================================
// Arquivo: build.gradle.kts (Nível de Projeto)
// Descrição: Configuração de build para o projeto Catfeina.
// =============================================================================

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
}
