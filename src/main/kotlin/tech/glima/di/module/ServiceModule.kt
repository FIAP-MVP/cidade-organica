package tech.glima.di.module

import org.koin.dsl.module
import tech.glima.service.CustomerService
import tech.glima.service.CustomerServiceImpl

object ServiceModule {

    val module = module {

        single<CustomerService> {
            CustomerServiceImpl()
        }
    }
}