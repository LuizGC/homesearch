plugins {
    kotlin("jvm")
    id("org.flywaydb.flyway") version "10.17.2"
    application
}

group = "com.homesearch"
version = "1.0-SNAPSHOT"

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.20.1")
    }
}

application {
    mainClass = "com.homesearch.osmparser.ParserKt"
}

dependencies {
    implementation("org.openstreetmap.osmosis:osmosis-core:0.49.2")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:0.49.2")
    implementation("org.geotools:gt-geojson:31.3")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:5432/homesearch_db"
    driver = "org.postgresql.Driver"
    user = "admin"
    password = "admin"
}
