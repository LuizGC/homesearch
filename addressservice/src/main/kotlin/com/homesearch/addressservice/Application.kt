package com.homesearch.addressservice

import com.homesearch.addressservice.plugins.configureStaticFile
import com.homesearch.addressservice.plugins.configureCityRouting
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main() {
    Class.forName("org.postgresql.Driver")
    embeddedServer(Netty, port = 8080, module = Application::module, watchPaths = listOf("classes"))
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureStaticFile()
    configureCityRouting()
}