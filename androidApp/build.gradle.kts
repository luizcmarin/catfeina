// =============================================================================
// Arquivo: androidApp.build.gradle.kts
// Descrição: Configuração de build para o módulo :androidApp.
// =============================================================================

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.composeHotReload)
}

android {
    namespace = "com.marin.catfeina"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int

        applicationId = "com.marin.catfeina.androidApp"
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
    }
}

kotlin {
    jvmToolchain( rootProject.extra["versionJDK"] as Int)
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.android)
}
