package tech.glima.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.json.JSONObject
import org.koin.ktor.ext.inject
import tech.glima.model.CultivationUnit
import tech.glima.service.CultivationUnitService

fun Route.cultivationUnit(){
    val cultivationUnitService : CultivationUnitService by inject()

    route("") {
        get {
            call.respond(cultivationUnitService.getAll())
        }

        get("/{id?}") {
            val id = call.parameters["id"]?.toInt()
            cultivationUnitService.get(id)?.let {
                call.respond(it)
            }
            // TODO MUDAR A LÓGICA SE NÃO HOUVER UNIDADES, REGRA DE NEGÓCIO NÃO PODE SER TRATADA COM HTTP CODE
            call.respond(HttpStatusCode.BadRequest, JSONObject())
        }

        put("/{id?}") {
            val cultivation = call.receive<CultivationUnit>()
            call.parameters["id"]?.let { id ->
                with(cultivation) {
                    cultivationUnitService.update(id = id.toInt(), name = name)
                }
                call.respond(status = HttpStatusCode.OK, "ok")
            }
        }

        post {
            val isCreated = with(call.receive<CultivationUnit>()) {
                cultivationUnitService.create(name = name)
            } != null

            if (isCreated) call.respond(status = HttpStatusCode.Created, "ok")
            else call.respond(status = HttpStatusCode.UnprocessableEntity, "Falha ao criar CultivationUnit")
        }

        delete("/{id?}") {
            call.parameters["id"]?.let { id ->
                val isDeleted = cultivationUnitService.delete(id.toInt())

                if (isDeleted) call.respond(message = HttpStatusCode.OK)
                else call.respond(message = HttpStatusCode.UnprocessableEntity)
            }
            call.respond(message = HttpStatusCode.BadRequest)
        }
    }
}