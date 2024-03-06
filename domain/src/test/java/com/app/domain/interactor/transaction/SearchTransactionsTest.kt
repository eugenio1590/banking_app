package com.app.domain.interactor.transaction

import com.app.domain.interactor.transaction.search.SearchTransactions
import com.app.domain.interactor.transaction.search.SearchTransactionsUseCase
import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.Month
import java.time.YearMonth

class SearchTransactionsTest {

    private val transactionRepository: TransactionRepository = mockk()
    private val searchTransactions: SearchTransactions = SearchTransactionsUseCase(transactionRepository)

    private val account = Account(id = 1, Account.Type.SAVING, 1000.0)

    @Test
    fun `should return a paginated transactions list for a given account`() = runBlocking {
        // Given
        val expectedTransactions = listOf(
            Transaction(Transaction.Type.DEPOSIT, 500.0),
            Transaction(Transaction.Type.WITHDRAWAL, 200.0)
        )
        coEvery { transactionRepository.findBy(account, any(), any()) }.returns(expectedTransactions)

        // When
        val transactions = searchTransactions(account = account, page = 1, limit = 10)

        // Then
        assertEquals(2, transactions.size)
        coVerify { transactionRepository.findBy(account, any(), any()) }
    }

    @Test(expected = Error::class)
    fun `when searching a paginated list should throw an exception on failure`(): Unit = runBlocking {
        // Given
        coEvery { transactionRepository.findBy(account, any(), any()) }.throws(Error("Unexpected error"))

        // When
        searchTransactions(account = account, page = 1, limit = 10)
    }

    @Test
    fun `should return all transactions made in a month of the year for a given account`() = runBlocking {
        // Given
        val date = YearMonth.of(2022, Month.AUGUST)
        val expectedTransactions = listOf(
            Transaction(Transaction.Type.DEPOSIT, 500.0),
            Transaction(Transaction.Type.WITHDRAWAL, 200.0)
        )
        coEvery { transactionRepository.findBy(account, any()) }.returns(expectedTransactions)

        // When
        val transactions = searchTransactions(account = account, month = date.month, year = date.year)

        // Then
        assertEquals(2, transactions.size)
        coVerify { transactionRepository.findBy(account, any()) }
    }

    @Test(expected = Error::class)
    fun `when searching all transactions should throw an exception on failure`(): Unit = runBlocking {
        // Given
        val date = YearMonth.of(2022, Month.AUGUST)
        coEvery { transactionRepository.findBy(account, any()) }.throws(Error("Unexpected error"))

        // When
        searchTransactions(account = account, month = date.month, year = date.year)
    }
}
