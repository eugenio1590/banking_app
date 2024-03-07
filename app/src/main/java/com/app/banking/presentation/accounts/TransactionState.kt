package com.app.banking.presentation.accounts

import com.app.banking.presentation.ErrorMessage

/**
 * Sealed interface representing the various states of a transaction operation can be in.
 */
sealed interface TransactionState {

    /**
     * Initial state indicating that no transaction operation has been initiated.
     */
    data object Initial : TransactionState

    /**
     * Loading state indicating that a transaction is in progress.
     */
    data object Loading : TransactionState

    /**
     * State indicating that a transaction operation has completed successfully.
     *
     */
    data object Success : TransactionState

    /**
     * State indicating that a transaction operation has failed.
     *
     * @param message The error message associated with the failure.
     */
    data class Failure(val message: ErrorMessage) : TransactionState
}
