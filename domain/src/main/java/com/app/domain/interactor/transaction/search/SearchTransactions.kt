package com.app.domain.interactor.transaction.search

import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.TransactionRepository
import java.time.Month

/**
 * Interface for searching and retrieving transactions associated with a specific account.
 *
 * This interface defines a contract for searching transactions.
 * Implementing classes are responsible for providing the actual implementation for transaction search.
 *
 * @see Transaction
 */
interface SearchTransactions {
    /**
     * Invokes the search operation to retrieve a paginated list of transactions.
     *
     * @param account The account for which transactions are being searched.
     * @param page The page number indicating the set of transactions to retrieve.
     * @param limit The maximum number of episodes to retrieve per page.
     * @return A list of [Transaction] objects associated with the account.
     */
    suspend operator fun invoke(account: Account, page: Int, limit: Int): List<Transaction>

    /**
     * Invokes the search operation to retrieve all transactions
     * made in the [month] and [year] for the specified account.
     *
     * @param account The account for which transactions are being searched.
     * @param month The month for which transactions are being retrieved.
     * @param year The year for which transactions are being retrieved.
     * @return A list of [Transaction] objects associated with the account within the month and year.
     */
    suspend operator fun invoke(account: Account, month: Month, year: Int): List<Transaction>

    object Factory {
        fun create(transactionRepository: TransactionRepository): SearchTransactions {
            return SearchTransactionsUseCase(transactionRepository)
        }
    }
}
