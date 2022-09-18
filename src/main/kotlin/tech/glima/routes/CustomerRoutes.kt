package tech.glima.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tech.glima.database.dao.dao
import tech.glima.model.Customer

fun Route.customerRouting() {
    route("") {
        get {
            call.respond(status = HttpStatusCode.OK, mapOf("customers" to dao.allCustomers()))
        }
        get("/{id?}") {
            val id = call.parameters["id"]
            if (id != null) {
                call.respond(status = HttpStatusCode.OK, mapOf("customer" to dao.customer(id.toInt())))
            } else call.respond(status = HttpStatusCode.BadRequest, message = "Id inválido")

        }
        put("/{id?}") {
            val customer = call.receive<Customer>()
            call.parameters["id"]?.let { id ->
                with(customer) {
                    dao.editCustomer(id = id.toInt(), name = name, email = email)
                }
                call.respond(status = HttpStatusCode.OK, "ok")

            }
        }
        post {
            val customer = call.receive<Customer>()
            with(customer) {
                dao.addNewCustomer(name = name, email = email)
            }
            call.respond(status = HttpStatusCode.Created, "ok")
        }

        delete("/{id?}") {
            val id = call.parameters["id"]
            if (id != null) {
                dao.deleteCustomer(id.toInt())
                call.respond(status = HttpStatusCode.OK, "Deletado com sucesso")
            } else call.respond(status = HttpStatusCode.BadRequest, message = "Id de usuário não encontrado")
        }
    }
}
