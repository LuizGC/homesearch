plugins {
    kotlin("jvm") version "2.0.10"
    id("org.flywaydb.flyway") version "10.17.2"
    application
}

group = "com.homesearch"
version = "1.0-SNAPSHOT"

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.7.2")
    }
}

repositories {
    maven {
        url = uri("https://repo.osgeo.org/repository/release/")
    }
    mavenCentral()
}

application {
    mainClass = "org.example.MainKt"
}

dependencies {
    implementation("org.openstreetmap.osmosis:osmosis-core:0.49.2")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:0.49.2")
    implementation("org.geotools:gt-geojson:31.3")
    implementation("org.postgresql:postgresql:42.7.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

flyway {
    url = "jdbc:postgresql://localhost:5432/homesearch_db"
    driver = "org.postgresql.Driver"
    user = "admin"
    password = "admin"
}
