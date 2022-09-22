package tech.glima.database.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tech.glima.model.Cities
import tech.glima.model.Customers

object DatabaseFactory {
    fun init() {
        val driverClassName = "oracle.jdbc.OracleDriver"
        val jdbcURL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
        val user = "RM92582"
        val password = "080502"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = user,
            password = password
        )

        transaction(database) {
            SchemaUtils.create(Customers)
            SchemaUtils.create(Cities)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}