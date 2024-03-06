package com.app.domain.exception

import com.app.domain.model.Transaction

/**
 * Represents a hierarchy of custom exceptions for handling various error scenarios.
 *
 * This sealed class provides a structured way to handle errors in the application.
 *
 * @param message  Optional message of the error.
 */
sealed class Error(override val message: String? = null) : Exception() {

    /**
     * Represents various errors related to transactions.
     *
     * @see Transaction
     */
    sealed class TransactionError : Error() {
        /**
         * Error indicating that the amount involved in the transaction is non-positive.
         */
        data object NonPositiveAmountError : TransactionError()

        /**
         * Error indicating that the account has a zero balance.
         */
        data object ZeroBalanceError : TransactionError()

        /**
         * Error indicating that the account has insufficient balance for a withdrawal transaction.
         */
        data object InsufficientBalanceError : TransactionError()

        /**
         * General error indicating a failure in transaction processing.
         */
        data object TransactionProcessingError : TransactionError()
    }
}
