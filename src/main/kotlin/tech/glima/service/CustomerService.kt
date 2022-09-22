package tech.glima.service

import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.Customer
import tech.glima.model.Customers

interface CustomerService {
    suspend fun list(): Map<String, List<Customer>>
    suspend fun get(id: Int?): Customer?
    suspend fun create(name: String, email: String): Customer?
    suspend fun update(id: Int, name: String, email: String): Boolean
    suspend fun delete(id: Int): Boolean
}

class CustomerServiceImpl : CustomerService {
    override suspend fun list(): Map<String, List<Customer>> {
        return mapOf("customers" to dbQuery {
            Customers.selectAll().map { row ->
                mapToCity(row)
            }
        })
    }

    override suspend fun get(id: Int?): Customer? {
        if (id != null) {
            return dbQuery {
                Customers
                    .select { Customers.id eq id }
                    .map(::mapToCity)
                    .singleOrNull()
            }
        }
        return null
    }

    override suspend fun create(name: String, email: String): Customer? = dbQuery {
        val insertQuery = Customers.insert {
            it[Customers.name] = name
            it[Customers.email] = email
        }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCity)
    }

    override suspend fun update(id: Int, name: String, email: String): Boolean {
        return dbQuery {
            Customers.update({ Customers.id eq id }) {
                it[Customers.name] = name
                it[Customers.email] = email
            } > 0
        }
    }


    override suspend fun delete(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }
}

private fun mapToCity(row: ResultRow) = Customer(
    id = row[Customers.id],
    name = row[Customers.name],
    email = row[Customers.email]
)