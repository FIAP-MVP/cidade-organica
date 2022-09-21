package tech.glima.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.json.JSONObject
import org.koin.ktor.ext.inject
import tech.glima.model.Customer
import tech.glima.service.CustomerService

fun Route.customerController() {

    val customerService: CustomerService by inject()

    route("") {
        get {
            call.respond(customerService.listAllCustomers())
        }

        get("/{id?}") {
            val id = call.parameters["id"]?.toInt()
            customerService.getCustomer(id)?.let {
                call.respond(it)
            }
            call.respond(HttpStatusCode.BadRequest, JSONObject())
        }

        put("/{id?}") {
            val customer = call.receive<Customer>()
            call.parameters["id"]?.let { id ->
                with(customer) {
                    customerService.update(id = id.toInt(), name = name, email = email)
                }
                call.respond(status = HttpStatusCode.OK, "ok")
            }
        }

        post {
            val isCreated = with(call.receive<Customer>()) {
                customerService.createCustomer(name = name, email = email)
            } != null

            if (isCreated) call.respond(status = HttpStatusCode.Created, "ok")
            else call.respond(status = HttpStatusCode.UnprocessableEntity, "Falha ao criar usuário")
        }

        delete("/{id?}") {
            call.parameters["id"]?.let { id ->
                val isDeleted = customerService.delete(id.toInt())

                if (isDeleted) call.respond(message = HttpStatusCode.OK)
                else call.respond(message = HttpStatusCode.UnprocessableEntity)
            }
            call.respond(message = HttpStatusCode.BadRequest)
        }
    }
}
