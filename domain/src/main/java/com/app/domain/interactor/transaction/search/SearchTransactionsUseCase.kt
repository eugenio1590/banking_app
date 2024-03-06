package com.app.domain.interactor.transaction.search

import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.TransactionRepository
import java.time.Month
import java.time.YearMonth

/**
 * Use case for searching and retrieving transactions.
 *
 * This internal class implements the [SearchTransactions] interface, providing a higher-level abstraction
 * for searching transactions. It delegates the actual search operation to the [TransactionRepository].
 *
 * @param transactionRepository The repository responsible for fetching transactions from a data source.
 */
internal class SearchTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) : SearchTransactions {
    override suspend fun invoke(account: Account, page: Int, limit: Int): List<Transaction> {
        return transactionRepository.findBy(account, page = page, limit = limit)
    }

    override suspend fun invoke(account: Account, month: Month, year: Int): List<Transaction> {
        return transactionRepository.findBy(account, date = YearMonth.of(year, month))
    }
}
