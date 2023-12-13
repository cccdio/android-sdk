pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials { username = "jp_p6sqn413tabm06raonhd10bp5h" }
        }
    }
}

rootProject.name = "cccd-io-kotlin-android"
include(":app")
include(":cccd-capture-sdk")
