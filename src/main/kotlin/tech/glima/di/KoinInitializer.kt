package tech.glima.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger
import tech.glima.di.module.ServiceModule

fun Application.startKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            listOf(
                ServiceModule.module
            )
        )
    }
    listenToKoinStatus()
}

private fun Application.listenToKoinStatus() {
    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }

    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }

    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }
}
