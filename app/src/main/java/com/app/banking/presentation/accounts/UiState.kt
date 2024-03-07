package com.app.banking.presentation.accounts

import com.app.domain.model.Account
import com.app.domain.model.Transaction

/**
 * Data class representing the UI state for a paginated episode set in the application.
 *
 * @property transactionType The type of transaction to perform.
 * @property loadState The current load state indicating the status of data loading.
 * @property transactionState The state of the transaction.
 */
data class UiState(
    val transactionType: Transaction.Type? = null,
    val loadState: LoadState = LoadState.Initial,
    val transactionState: TransactionState = TransactionState.Initial
) {
    /**
     * Check if deposit is available based on the current UI state.
     *
     * @return True if deposit is available, false otherwise.
     */
    val isDepositAvailable: Boolean
        get() = (loadState as? LoadState.Success)?.accounts?.isNotEmpty() ?: false

    /**
     * Check if withdrawal is available based on the current UI state.
     *
     * @return True if withdrawal is available, false otherwise.
     */
    val isWithdrawalAvailable: Boolean
        get() = (loadState as? LoadState.Success)?.accounts?.isNotEmpty() ?: false

    /**
     * The loaded account list.
     */
    val accounts: List<Account>
        get() = (loadState as? LoadState.Success)?.accounts ?: emptyList()
}
