package com.app.banking.presentation

import com.app.domain.exception.Error.TransactionError

/**
 * Converts exceptions into a standardized [ErrorMessage] model.
 *
 * This class provides a centralized way to convert various exceptions into a common
 * [ErrorMessage] model for consistent error handling and reporting.
 */
open class ErrorConverter {

    /**
     * Converts the given exception into an [ErrorMessage] model.
     *
     * @param error The exception to be converted.
     * @return An instance of [ErrorMessage] representing the converted error information.
     */
    open fun convert(error: Exception): ErrorMessage = when (error) {
        is TransactionError.NonPositiveAmountError ->
            ErrorMessage.NonPositiveAmountMessage
        is TransactionError.ZeroBalanceError ->
            ErrorMessage.ZeroBalanceMessage
        is TransactionError.InsufficientBalanceError ->
            ErrorMessage.InsufficientBalanceMessage
        is TransactionError.TransactionProcessingError ->
            ErrorMessage.TransactionProcessingMessage
        else -> ErrorMessage.UnknownMessage
    }
}
