package tech.glima.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String? = null, val firstName: String?, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>()
