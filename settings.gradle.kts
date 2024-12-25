pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.1.0"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
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
