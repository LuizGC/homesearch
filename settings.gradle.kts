pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.0.21"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://repo.osgeo.org/repository/release/")
        }
        mavenCentral()
    }
}


rootProject.name = "homesearch"
include("osmparser")
include("addressservice")
include("database")
