package tech.glima.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tech.glima.model.customerStorage

fun Route.customerRouting() {
    route("/") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {

        }
        post {

        }
        delete("{id?}") {

        }
    }
}