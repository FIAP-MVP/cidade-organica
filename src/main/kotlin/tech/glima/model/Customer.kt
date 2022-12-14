package tech.glima.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Customer(val id: Int? = null, val name: String, val email: String)

object Customers : Table() {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 60)
    val email = varchar("email", 120)

    override val primaryKey = PrimaryKey(id)
}