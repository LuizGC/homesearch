plugins {
    kotlin("jvm") version "2.0.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

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
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}