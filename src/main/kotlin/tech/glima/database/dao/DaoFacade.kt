package tech.glima.database.dao

import tech.glima.model.Customer

interface DaoFacade {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: Int): Customer?
    suspend fun addNewCustomer(name: String, email: String): Customer?
    suspend fun editCustomer(id: Int, name: String, email: String): Boolean
    suspend fun deleteCustomer(id: Int): Boolean
}