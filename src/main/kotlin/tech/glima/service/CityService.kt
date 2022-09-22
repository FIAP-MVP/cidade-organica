package tech.glima.service

import org.jetbrains.exposed.sql.*
import tech.glima.database.dao.DatabaseFactory.dbQuery
import tech.glima.model.Cities
import tech.glima.model.City

interface CityService {
    suspend fun getAll(): Map<String, List<City>>
    suspend fun get(id: Int?): City?
    suspend fun create(name: String, populationNumber: Int): City?
    suspend fun update(id: Int, name: String, populationNumber: Int): Boolean
    suspend fun delete(id: Int): Boolean
}

class CityServiceImpl : CityService {
    override suspend fun getAll(): Map<String, List<City>> {
        return mapOf("city" to dbQuery {
            Cities.selectAll().map { cityResult ->
                mapToCity(cityResult)
            }
        })
    }

    override suspend fun get(id: Int?): City? {
        if (id != null) {
            return dbQuery {
                Cities
                    .select { Cities.id eq id }
                    .map(::mapToCity)
                    .singleOrNull()
            }
        }
        return null
    }

    override suspend fun create(name: String, populationNumber: Int): City? = dbQuery {
        val insertQuery = Cities
            .insert { city ->
                city[Cities.name] = name
                city[Cities.populationNumber] = populationNumber
            }
        insertQuery.resultedValues?.singleOrNull()?.let(::mapToCity)
    }

    override suspend fun update(id: Int, name: String, populationNumber: Int): Boolean {
        return dbQuery {
            Cities.update({ Cities.id eq id }) { city ->
                city[Cities.name] = name
                city[Cities.populationNumber] = populationNumber
            } > 0
        }
    }


    override suspend fun delete(id: Int): Boolean = dbQuery {
        Cities
            .deleteWhere { Cities.id eq id } > 0
    }
}

private fun mapToCity(row: ResultRow) = City(
    id = row[Cities.id],
    name = row[Cities.name],
    populationNumber = row[Cities.populationNumber]
)
