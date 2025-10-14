// =============================================================================
// Arquivo: shared.build.gradle.kts
// Descrição: Configuração de build para o módulo :shared.
//            Este módulo contém todo o código compartilhado entre as plataformas.
// =============================================================================
@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    android {
        namespace = "com.marin.catfeina"
        compileSdk = rootProject.extra["compileSdk"] as Int
        minSdk = rootProject.extra["minSdk"] as Int
        androidResources.enable = true
    }

    jvm("desktop")

    js { browser() }
    wasmJs { browser() }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.bundles.commons)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.bundles.desktop)
        }

        iosMain.dependencies {
            implementation(libs.bundles.ios)
        }

        jsMain.dependencies {
            implementation(libs.bundles.js)
        }
    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries { framework { baseName = "shared" } }
        }
}

buildkonfig {
    // BuildKonfig configuration here.
    // https://github.com/yshrsmz/BuildKonfig#gradle-configuration
    packageName = "com.marin.catfeina"
    defaultConfigs {
    }
}

sqldelight {
    databases {
        create("CatfeinaDatabase") {
            packageName.set("com.marin.catfeina.db")
            // Desabilita a verificação de migração para evitar erros de permissão no Windows
            // e por não termos um banco de dados legado para migrar.
            verifyMigrations.set(false)
        }
    }
}
