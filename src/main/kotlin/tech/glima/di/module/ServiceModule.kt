package tech.glima.di.module

import org.koin.dsl.module
import tech.glima.service.*

object ServiceModule {

    val module = module {

        single<CustomerService> {
            CustomerServiceImpl()
        }
        single<CityService> {
            CityServiceImpl()
        }
        single<CultivationUnitService>{
            CultivationUnitUnitServiceImpl()
        }
    }
}