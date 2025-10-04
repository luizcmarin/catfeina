// =============================================================================
// Arquivo: app/build.gradle.kts
// Descrição: Configuração otimizada do Gradle para o módulo :app.
// =============================================================================

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.marin.catfeina"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.marin.catfeina"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        buildConfigField("int", "VERSION_CODE", libs.versions.versionCode.get())
        buildConfigField("String", "VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
    }

    buildTypes {
        debug {
            isCrunchPngs = false
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
        jniLibs {
            useLegacyPackaging = true
            excludes += "lib/armeabi/libjnidispatch.so"
            jniLibs.keepDebugSymbols.addAll(
                listOf(
                    "**/*libc++_shared.so",
                    "**/*libdatastore_shared_counter.so",
                    "**/*librive-android.so",
                    "**/*libandroidx.graphics.path.so"
                )
            )
        }
    }

    lint {
        enable.add("DuplicateStrings")
        sarifReport = true
        abortOnError = false
        checkReleaseBuilds = true
        warningsAsErrors = false
    }
}

dependencies {
    // Bundles definidos no libs.versions.toml
    implementation(libs.bundles.core)
    implementation(libs.bundles.data)
    implementation(libs.bundles.navigation)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.startup.runtime)
    debugImplementation(libs.androidx.compose.ui.tooling) // ui-tooling apenas para debug

    // Bibliotecas individuais
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.coil.compose)
    implementation(libs.rive.android)

    // Injeção de Dependência (Hilt)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // JSON Parser (Moshi)
    ksp(libs.moshi.codegen)

    ksp(libs.androidx.room.compiler)

}

// =============================================================================
// Tarefas: Abertura automática de relatórios Lint (Mantidas como estavam)
// =============================================================================

fun abrirRelatorioLint(path: String) {
    val os = System.getProperty("os.name").lowercase()
    val comando = when {
        os.contains("win") -> arrayOf("cmd", "/c", "start", "\"\"", path)
        os.contains("mac") -> arrayOf("open", path)
        os.contains("nix") || os.contains("nux") || os.contains("aix") -> arrayOf("xdg-open", path)
        else -> null
    }
    comando?.let { Runtime.getRuntime().exec(it) }
        ?: println("SO não suportado para abrir relatório.")
}

tasks.register("openDebugLintReport") {
    description = "Abre o relatório HTML do Lint para a variante debug."
    group = "Verification"
    val reportFile = layout.buildDirectory.file("reports/lint-results-debug.html")
    doLast {
        val file = reportFile.get().asFile
        if (file.exists()) abrirRelatorioLint(file.absolutePath)
        else println("Relatório Debug não encontrado: ${file.absolutePath}")
    }
}

tasks.register("openReleaseLintReport") {
    description = "Abre o relatório HTML do Lint para a variante release."
    group = "Verification"
    val reportFile = layout.buildDirectory.file("reports/lint-results-release.html")
    doLast {
        val file = reportFile.get().asFile
        if (file.exists()) abrirRelatorioLint(file.absolutePath)
        else println("Relatório Release não encontrado: ${file.absolutePath}")
    }
}

project.afterEvaluate {
    listOf("lintDebug", "lintVitalReportDebug", "reportLintDebug").firstNotNullOfOrNull {
        tasks.findByName(it)
    }?.finalizedBy(tasks.named("openDebugLintReport"))

    listOf("lintRelease", "lintVitalReportRelease", "reportLintRelease").firstNotNullOfOrNull {
        tasks.findByName(it)
    }?.finalizedBy(tasks.named("openReleaseLintReport"))
}


//
//val copiarDadosParaAssets = tasks.register("copiarDadosParaAssets") {
//    dependsOn("limparAssets")
//    doLast {
//        if (!dbOrigem.exists()) throw GradleException("Banco de dados não encontrado: ${dbOrigem.absolutePath}")
//        if (!imagensOrigem.exists() || !imagensOrigem.isDirectory) throw GradleException("Pasta de imagens inválida: ${imagensOrigem.absolutePath}")
//
//        pastaDbDestino.mkdirs()
//        pastaImagensDestino.mkdirs()
//
//        dbOrigem.copyTo(File(pastaDbDestino, dbOrigem.name), overwrite = true)
//
//        imagensOrigem.walkTopDown().filter { it.isFile }.forEach { arquivo ->
//            val destinoRelativo = arquivo.relativeTo(imagensOrigem)
//            val destinoFinal = File(pastaImagensDestino, destinoRelativo.path)
//            destinoFinal.parentFile.mkdirs()
//            arquivo.copyTo(destinoFinal, overwrite = true)
//        }
//
//        val dbCopiado = File(pastaDbDestino, dbOrigem.name).exists()
//        val imagensCopiadas = pastaImagensDestino.listFiles()?.isNotEmpty() == true
//
//        println("Banco de dados: ${if (dbCopiado) "copiado com sucesso." else "falhou."}")
//        println("Imagens: ${if (imagensCopiadas) "copiadas com sucesso." else "falharam."}")
//
//        if (!dbCopiado || !imagensCopiadas) throw GradleException("Falha na cópia dos dados. Verifique os arquivos e permissões.")
//    }
//}
//
//tasks.named("preBuild") {
//    dependsOn(copiarDadosParaAssets)
//}
