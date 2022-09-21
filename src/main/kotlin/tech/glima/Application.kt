package tech.glima

import io.ktor.server.application.*
import tech.glima.database.dao.DatabaseFactory
import tech.glima.di.startKoin
import tech.glima.plugins.*

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.main() {
    startKoin()
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}

