package tech.glima.database.dao

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.Customer
import tech.glima.model.Customers

class DaoFacadeImpl : DaoFacade {

    private fun mapToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        name = row[Customers.name],
        email = row[Customers.email]
    )

    override suspend fun allCustomers() = dbQuery {
        Customers.selectAll().map { row ->
            mapToCustomer(row)
        }
    }

    override suspend fun customer(id: Int): Customer? = dbQuery {
        Customers
            .select { Customers.id eq id }
            .map(::mapToCustomer)
            .singleOrNull()
    }

    override suspend fun addNewCustomer(name: String, email: String): Customer? = dbQuery {
        val insertQuery = Customers.insert {
            it[Customers.name] = name
            it[Customers.email] = email
        }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCustomer)
    }

    override suspend fun editCustomer(id: Int, name: String, email: String): Boolean = dbQuery {
        Customers.update({ Customers.id eq id }) {
            it[Customers.name] = name
            it[Customers.email] = email
        } > 0
    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }
}

val dao: DaoFacade = DaoFacadeImpl().apply {
    runBlocking {
        if(allCustomers().isEmpty()){
            addNewCustomer(name = "Padre Guguinha", email = "email@fiap.com")
        }
    }
}