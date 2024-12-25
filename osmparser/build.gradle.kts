plugins {
    kotlin("jvm")
    application
}

group = "com.homesearch"
version = "1.0-SNAPSHOT"

application {
    mainClass = "com.homesearch.osmparser.ParserKt"
}

dependencies {
    implementation("org.openstreetmap.osmosis:osmosis-core:0.49.2")
    implementation("org.openstreetmap.osmosis:osmosis-pbf2:0.49.2")
    implementation("org.geotools:gt-geojson:32.1")
    implementation(project(":database"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
