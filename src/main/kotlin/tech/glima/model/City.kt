package tech.glima.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class City (val id: Int? = null, val name : String, val populationNumber : Int)

object Citys : Table() {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 80)
    val populationNumber = integer("populationNumber")

    override val primaryKey = PrimaryKey(id)
}