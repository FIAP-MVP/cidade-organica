package tech.glima.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

// TODO adicionar atributo Estoque (outra Dataclass) para a dataclass (descobrir como criar relacionamento)
@Serializable
data class CultivationUnit (val id: Int? = null, val name : String)

object CultivationUnits : Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 80)

    override val primaryKey = PrimaryKey(id)
}