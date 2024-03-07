package com.app.banking.presentation.accounts

import com.app.banking.presentation.ErrorMessage
import com.app.domain.model.Account

/**
 * Sealed interface representing the various states of loading for user's account list.
 */
sealed interface LoadState {

    /**
     * Initial state indicating that the data is not loaded yet.
     */
    data object Initial : LoadState

    /**
     * Loading state indicating that data is currently being fetched.
     */
    data object Loading : LoadState

    /**
     * Success state indicating that the data has been successfully loaded.
     *
     * @param accounts The list of accounts loaded successfully.
     */
    data class Success(val accounts: List<Account>) : LoadState

    /**
     * Failure state indicating that an error occurred while loading the data.
     *
     * @param message The error message representing the loading failure.
     */
    data class Failure(val message: ErrorMessage) : LoadState
}
