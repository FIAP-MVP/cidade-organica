package tech.glima.controller

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.json.JSONObject
import org.koin.ktor.ext.inject
import tech.glima.model.City
import tech.glima.service.CityService

fun Route.cityController() {

    val cityService: CityService by inject()

    route("") {
        get {
            call.respond(cityService.listAllCitys())
        }

        get("/{id?}") {
            val id = call.parameters["id"]?.toInt()
            cityService.getCity(id)?.let {
                call.respond(it)
            }
            call.respond(HttpStatusCode.BadRequest, JSONObject())
        }

        put("/{id?}") {
            val city = call.receive<City>()
            call.parameters["id"]?.let { id ->
                with(city) {
                    cityService.update(id = id.toInt(), name = name, populationNumber = populationNumber)
                }
                call.respond(status = HttpStatusCode.OK, "ok")
            }
        }

        post {
            val isCreated = with(call.receive<City>()) {
                cityService.createCity(name = name, populationNumber = populationNumber)
            } != null

            if (isCreated) call.respond(status = HttpStatusCode.Created, "ok")
            else call.respond(status = HttpStatusCode.UnprocessableEntity, "Falha ao criar usuário")
        }

        delete("/{id?}") {
            call.parameters["id"]?.let { id ->
                val isDeleted = cityService.delete(id.toInt())

                if (isDeleted) call.respond(message = HttpStatusCode.OK)
                else call.respond(message = HttpStatusCode.UnprocessableEntity)
            }
            call.respond(message = HttpStatusCode.BadRequest)
        }
    }
}
