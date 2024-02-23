import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.ExperimentalComposeLibrary
import kotlin.io.path.div

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("kotlin-parcelize")
}



repositories {
    google()
    mavenCentral()
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting


    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.material3)
            implementation(libs.coil.compose)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            /*implementation(libs.androidx.lifecycle.common)*/
            implementation(libs.kotlinx.coroutines.test)
            /*implementation(libs.json.json)*/
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.test)
        }

        commonMain.dependencies {
            implementation(libs.webcam.capture)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.androidx.compiler)
            implementation(libs.bumble.appyx.navigation)
            implementation(libs.qrcode.kotlin)




            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bumble.appyx.navigation)
            implementation(libs.qrcode.kotlin)
            implementation(libs.okhttp)
            implementation(libs.json)
            implementation(libs.bumble.appyx.navigation)
            api(libs.backstack)
            }
        }
    }
}