// =============================================================================
// Arquivo: desktopApp.build.gradle.kts
// Descrição: Configuração de build para o módulo :desktopApp.
// =============================================================================
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.gradle.ComposeHotRun

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.composeHotReload)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            targetFormats( TargetFormat.Msi)
            packageName = rootProject.extra["packageName"] as String
            packageVersion = rootProject.extra["packageVersion"] as String

            windows {
                iconFile.set(project.file("appIcons/WindowsIcon.ico"))
            }
//            linux {
//                iconFile.set(project.file("appIcons/LinuxIcon.png"))
//            }
//            macOS {
//                iconFile.set(project.file("appIcons/MacosIcon.icns"))
//                bundleID = "com.marin.catfeina.desktopApp"
//            }
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
}

tasks.withType<ComposeHotRun>().configureEach {
    mainClass = "MainKt"
}
