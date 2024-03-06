package com.app.domain.repository

import com.app.domain.model.Account
import com.app.domain.model.User

/**
 * Repository interface for retrieving accounts.
 *
 * This interface defines a contract for fetching financial accounts from a data source.
 */
interface AccountRepository {
    /**
     * Find and retrieve a list of accounts.
     *
     * @param user The user whose accounts are being searches.
     *
     * @return A [List] of [Account] objects representing the search results for the specified user.
     *
     * @throws Exception If there is an error during the retrieval operation.
     */
    suspend fun findBy(user: User): List<Account>

    /**
     * Updates an account in the repository.
     *
     * @param account The account to be updated in the repository.
     * @return `true` if the account was successfully updated; `false` otherwise.
     */
    suspend fun update(account: Account): Boolean
}
