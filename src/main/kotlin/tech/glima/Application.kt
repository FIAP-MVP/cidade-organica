package tech.glima

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import tech.glima.database.dao.DatabaseFactory
import tech.glima.di.startKoin
import tech.glima.plugins.configureRouting
import tech.glima.plugins.configureSerialization

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.main() {
    startKoin()
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}
