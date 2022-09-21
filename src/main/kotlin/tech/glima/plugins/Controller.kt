package tech.glima.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tech.glima.controller.cityController
import tech.glima.controller.customerController

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/customer") {
            customerController()
        }
        route("/city"){
            cityController()
        }
    }
}

