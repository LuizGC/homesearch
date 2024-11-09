package com.homesearch.addressservice.routers

import com.homesearch.common.db.cities.listCities
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureCityRouting() {
    routing {
        get("/cities") {
            call.respond(listCities())
        }
    }
}