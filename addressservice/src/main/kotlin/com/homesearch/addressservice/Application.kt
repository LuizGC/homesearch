package com.homesearch.addressservice

import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing


fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            staticResources("/", "static", index = "index.html")
        }
    }.start(wait = true)
}