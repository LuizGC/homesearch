plugins {
    kotlin("jvm") version "2.0.21"
}

buildscript {
    repositories {
        mavenCentral()
    }
}

allprojects {
    tasks.withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    repositories {
        maven {
            url = uri("https://repo.osgeo.org/repository/release/")
        }
        mavenCentral()
    }
}
