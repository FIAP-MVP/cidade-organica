package tech.glima.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import tech.glima.model.Customer
import tech.glima.routes.customerRouting

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/customer") {
            customerRouting()
        }
    }
}

