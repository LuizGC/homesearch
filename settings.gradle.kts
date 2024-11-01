pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.0.21"
    }
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

