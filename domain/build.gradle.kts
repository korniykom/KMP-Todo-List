plugins {
    alias(libs.plugins.kotlinMultiplatform)
}


kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class) wasmJs {
        browser()
        binaries.executable()
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }

    }
}
