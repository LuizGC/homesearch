plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "3.0.1"
}

group = "com.homesearch.addressservice"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.homesearch.addressservice.ApplicationKt")
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
