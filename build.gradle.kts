// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.gms.services) apply false
}

subprojects {
    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            if (!flavorDimensions.contains("env")) {
                flavorDimensions += "env"
            }
            productFlavors.apply {
                maybeCreate("dev").apply { dimension = "env" }
                maybeCreate("qa").apply { dimension = "env" }
                maybeCreate("prod").apply { dimension = "env" }
            }
        }
    }
}