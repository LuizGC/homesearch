package com.homesearch.addressservice.plugins

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

fun Application.configureStaticFile() {
    routing {
        staticResources("/", "static", index = "index.html")
        staticResources("/js", "static/js")
        staticResources("/css", "static/css")
    }
}