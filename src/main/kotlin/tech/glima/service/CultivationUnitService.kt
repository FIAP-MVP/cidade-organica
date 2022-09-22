package tech.glima.service

import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.CultivationUnit
import tech.glima.model.CultivationUnits

interface CultivationUnitService {
    suspend fun getAll(): Map<String, List<CultivationUnit>>
    suspend fun get(id: Int?): CultivationUnit?
    suspend fun create(name: String): CultivationUnit?
    suspend fun update(id: Int, name: String): Boolean
    suspend fun delete(id: Int): Boolean
}

class CultivationUnitUnitServiceImpl : CultivationUnitService {
    override suspend fun getAll(): Map<String, List<CultivationUnit>> {
        return mapOf("cultivationUnit" to dbQuery {
            CultivationUnits.selectAll().map { cultivationUnitResult ->
                mapToCultivationUnit(cultivationUnitResult)
            }
        })
    }

    override suspend fun get(id: Int?): CultivationUnit? {
        if (id != null) {
            return dbQuery {
                CultivationUnits
                    .select { CultivationUnits.id eq id }
                    .map(::mapToCultivationUnit)
                    .singleOrNull()
            }
        }
        return null
    }

    override suspend fun create(name: String): CultivationUnit? = dbQuery {
        val insertQuery = CultivationUnits.insert { cultivationUnit ->
            cultivationUnit[CultivationUnits.name] = name
        }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCultivationUnit)
    }

    override suspend fun update(id: Int, name: String): Boolean {
        return dbQuery {
            CultivationUnits
                .update({ CultivationUnits.id eq id }) { cultivationUnit ->
                    cultivationUnit[CultivationUnits.name] = name
                } > 0
        }
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        CultivationUnits.deleteWhere { CultivationUnits.id eq id } > 0
    }

}

private fun mapToCultivationUnit(row: ResultRow) = CultivationUnit(
    id = row[CultivationUnits.id],
    name = row[CultivationUnits.name],
)