package com.app.banking.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.banking.R

/**
 * Represents an error message with associated resources for message text and image.
 *
 * @param message The resource ID for the error message text.
 * @param image The resource ID for the error image.
 */
sealed class ErrorMessage(@StringRes val message: Int, @DrawableRes val image: Int? = null) {

    data object NonPositiveAmountMessage : ErrorMessage(R.string.error_non_positive_amount)

    data object ZeroBalanceMessage : ErrorMessage(R.string.error_zero_balance)

    data object InsufficientBalanceMessage : ErrorMessage(R.string.error_insufficient_balance)

    data object TransactionProcessingMessage : ErrorMessage(R.string.error_transaction_processing)

    data object UnknownMessage : ErrorMessage(R.string.error_unknown)
}
