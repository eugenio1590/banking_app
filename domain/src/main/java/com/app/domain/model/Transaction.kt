package com.app.domain.model

/**
 * A business class representing a financial transaction.
 *
 * @property type The type of transaction, as defined by the [Type] enum.
 * @property amount The amount of money involved in the transaction.
 * @property account An optional [Account] associated with the transaction.
 */
data class Transaction(val type: Type, val amount: Double, val account: Account? = null) {

    /**
     * Enum class defining the types of transactions.
     */
    enum class Type {
        DEPOSIT,
        WITHDRAWAL
    }
}
