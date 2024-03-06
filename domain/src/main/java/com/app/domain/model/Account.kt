package com.app.domain.model

/**
 * A business class representing a financial account.
 *
 * @property id The unique identifier for the account.
 * @property type The type of account, as defined by the [Type] enum.
 * @property balance The current balance of the account.
 */
data class Account(val id: Int, val type: Type, val balance: Double) {

    /**
     * Enum class defining the types of financial accounts.
     */
    enum class Type {
        CURRENT, // Represents a current/checking account
        SAVING   // Represents a savings account
    }
}
