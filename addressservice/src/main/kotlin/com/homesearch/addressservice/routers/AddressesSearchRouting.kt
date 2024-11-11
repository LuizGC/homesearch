package com.homesearch.addressservice.routers

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureAddressesSearch() {
    routing {
        get("/find/{city}/addresses") {
            val city: String? = call.parameters["city"] ?: ""
            call.respondText("{\"city\":${city}}")
        }
    }
}