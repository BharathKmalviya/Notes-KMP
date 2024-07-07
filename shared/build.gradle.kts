plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.2"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // sql-delight runtime
                implementation(libs.runtime.v200)
                // flows support for sql-delight
                implementation(libs.coroutines.extensions)
                // date-time
                implementation(libs.kotlinx.datetime.v041)
            }
        }

        val androidMain by getting {
            dependencies {
                // sql-delight driver - Android
                implementation(libs.android.driver)
            }
        }

        val iosMain by getting {
            dependencies {
                // sql-delight driver - ios
                implementation(libs.native.driver.v200)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                // coroutines
                implementation(libs.kotlinx.coroutines.test.v173)
                // junit
                implementation(libs.junit)
                implementation(libs.androidx.core)
                // instrumentation test runner
                implementation(libs.androidx.runner)
            }
        }
    }
}

android {
    namespace = "com.example.notes"
    compileSdk = 34
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = 24
    }
}

