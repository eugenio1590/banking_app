package com.app.banking.presentation.accounts

import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.model.User

/**
 * Sealed interface representing different user intents for the accounts screen.
 */
sealed interface ViewIntent {
    /**
     * Intent to load the user's accounts.
     *
     * @property user The user for whom accounts are to be loaded.
     */
    class LoadAccounts(val user: User) : ViewIntent

    /**
     * Sealed interface representing various intents related to transaction forms.
     */
    sealed interface TransactionFormIntent : ViewIntent {

        /**
         * Intent to show or display a transaction form of a specific type.
         *
         * @param type The type of the transaction form to be displayed.
         */
        class Show(val type: Transaction.Type) : TransactionFormIntent

        /**
         * Intent to hide or dismiss the transaction form.
         */
        data object Hide : TransactionFormIntent
    }

    /**
     * Intent to make a deposit for a specified account.
     *
     * @property account The account for which a deposit is to be made.
     */
    class Deposit(val account: Account, val amount: Double) : ViewIntent

    /**
     * Intent to make a withdrawal for a specified account.
     *
     * @property account The account for which a withdrawal is to be made.
     */
    class Withdrawal(val account: Account, val amount: Double) : ViewIntent
}
