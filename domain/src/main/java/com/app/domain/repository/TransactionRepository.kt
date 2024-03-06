package com.app.domain.repository

import com.app.domain.model.Account
import com.app.domain.model.Transaction
import java.time.YearMonth

/**
 * Repository interface for retrieving transactions.
 *
 * This interface defines a contract for fetching transactions from a data source.
 */
interface TransactionRepository {
    /**
     * Find and retrieve a paginated list of transactions.
     *
     * @param account The account for which transactions are being retrieved.
     * @param page The page number indicating the set of transactions to retrieve.
     * @param limit The maximum number of transactions to be retrieved per page.
     * @return A list of transactions for the specified account within the specified page and limit.
     */
    suspend fun findBy(account: Account, page: Int, limit: Int): List<Transaction>

    /**
     * Find and retrieve all transactions made in a month of the year.
     *
     * @param account The account for which transactions are being retrieved.
     * @param date The year and month for which transactions are being retrieved.
     * @return A list of transactions for the specified account within the specified [date].
     */
    suspend fun findBy(account: Account, date: YearMonth): List<Transaction>

    /**
     * Adds a transaction to the repository.
     *
     * @param transaction The transaction to be added to the repository.
     * @return `true` if the transaction was successfully added; `false` otherwise.
     */
    suspend fun add(transaction: Transaction): Boolean
}
