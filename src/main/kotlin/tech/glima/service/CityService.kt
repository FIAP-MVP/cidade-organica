package tech.glima.service

import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.City
import tech.glima.model.Citys

interface CityService {
    suspend fun listAllCitys(): Map<String, List<City>>
    suspend fun getCity(id: Int?): City?
    suspend fun createCity(name: String, populationNumber: Int): City?
    suspend fun update(id: Int, name: String, populationNumber: Int): Boolean
    suspend fun delete(id: Int): Boolean
}

class CityServiceImpl : CityService {
    override suspend fun listAllCitys(): Map<String, List<City>> {
        return mapOf("city" to dbQuery {
            Citys.selectAll().map { row ->
                mapToCity(row)
            }
        })
    }

    override suspend fun getCity(id: Int?): City? {
        if (id != null) {
            return dbQuery {
                Citys
                    .select { Citys.id eq id }
                    .map(::mapToCity)
                    .singleOrNull()
            }
        }
        return null
    }

    override suspend fun createCity(name: String, populationNumber: Int): City? = dbQuery {
        val insertQuery = Citys.insert {
            it[Citys.name] = name
            it[Citys.populationNumber] = populationNumber
        }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCity)
    }

    override suspend fun update(id: Int, name: String, populationNumber: Int): Boolean {
        return dbQuery {
            Citys.update({ Citys.id eq id }) {
                it[Citys.name] = name
                it[Citys.populationNumber] = populationNumber
            } > 0
        }
    }


    override suspend fun delete(id: Int): Boolean = dbQuery {
        Citys.deleteWhere { Citys.id eq id } > 0
    }
}

private fun mapToCity(row: ResultRow) = City(
    id = row[Citys.id],
    name = row[Citys.name],
    populationNumber = row[Citys.populationNumber]
)
