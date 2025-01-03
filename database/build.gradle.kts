plugins {
    kotlin("jvm")
}

group = "com.homesearch"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.flywaydb:flyway-database-postgresql:11.1.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}