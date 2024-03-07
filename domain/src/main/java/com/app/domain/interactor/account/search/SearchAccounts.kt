package com.app.domain.interactor.account.search

import com.app.domain.model.Account
import com.app.domain.model.User
import com.app.domain.repository.AccountRepository

/**
 * Interface for searching and retrieving accounts associated with a user.
 *
 * This interface defines a contract for searching user's accounts.
 * Implementing classes are responsible for providing the actual implementation for account search.
 *
 * @see Account
 */
interface SearchAccounts {
    /**
     * Invokes the search operation to retrieve a list of accounts.
     *
     * @param user The user whose accounts are being searches.
     *
     * @return A [List] of [Account] objects representing the search results for the specified page.
     *
     * @throws Exception If there is an error during the search operation.
     */
    suspend operator fun invoke(user: User): List<Account>

    object Factory {
        fun create(accountRepository: AccountRepository): SearchAccounts {
            return SearchAccountsUseCase(accountRepository)
        }
    }
}
