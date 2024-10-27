package com.homesearch.addressservice

import io.ktor.server.engine.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            staticResources("/", "static", index = "index.html")
        }
    }.start(wait = true)
}