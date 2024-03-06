package com.app.domain.model

/**
 * A business class representing a user that has [Account]s in the bank.
 *
 * @property id The unique identifier for the user.
 * @property name The name of the user.
 */
data class User(val id: Int, val name: String)
