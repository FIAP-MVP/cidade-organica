package tech.glima.service

import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.Customer
import tech.glima.model.Customers

interface CustomerService {
    suspend fun listAllCustomers(): Map<String, List<Customer>>
    suspend fun getCustomer(id: Int?): Customer?
    suspend fun createCustomer(name: String, email: String): Customer?
    suspend fun update(id: Int, name: String, email: String): Boolean
    suspend fun deleteCustomer(id: Int): Boolean
}

class CustomerServiceImpl : CustomerService {
    override suspend fun listAllCustomers(): Map<String, List<Customer>> {
        return mapOf("customers" to dbQuery {
            Customers.selectAll().map { row ->
                mapToCustomer(row)
            }
        })
    }

    override suspend fun getCustomer(id: Int?): Customer? {
        if (id != null) {
            return dbQuery {
                Customers
                    .select { Customers.id eq id }
                    .map(::mapToCustomer)
                    .singleOrNull()
            }
        }
        return null
    }

    override suspend fun createCustomer(name: String, email: String): Customer? = dbQuery {
        val insertQuery = Customers.insert {
            it[Customers.name] = name
            it[Customers.email] = email
        }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCustomer)
    }

    override suspend fun update(id: Int, name: String, email: String): Boolean {
        return dbQuery {
            Customers.update({ Customers.id eq id }) {
                it[Customers.name] = Customers.name
                it[Customers.email] = Customers.email
            } > 0
        }
    }


    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }
}

private fun mapToCustomer(row: ResultRow) = Customer(
    id = row[Customers.id],
    name = row[Customers.name],
    email = row[Customers.email]
)