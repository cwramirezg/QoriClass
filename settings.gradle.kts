pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id(
        "org.gradle.toolchains.foojay-resolver-convention"
    ) version "1.0.0"
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
include(":core:common")
include(":core:designsystem")
include(":core:presentation")
include(":features:authentication")
include(":features:classroom")
include(":features:home")
include(":features:splash")
include(":layers:data")
include(":layers:domain")
