pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "QoriClass"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":design")
include(":features:authentication")
include(":features:classroom")
include(":features:home")
include(":features:splash")
