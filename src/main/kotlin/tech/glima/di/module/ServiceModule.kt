package tech.glima.di.module

import org.koin.dsl.module
import tech.glima.service.CityService
import tech.glima.service.CityServiceImpl
import tech.glima.service.CustomerService
import tech.glima.service.CustomerServiceImpl

object ServiceModule {

    val module = module {

        single<CustomerService> {
            CustomerServiceImpl()
        }
        single<CityService> {
            CityServiceImpl()
        }
    }
}