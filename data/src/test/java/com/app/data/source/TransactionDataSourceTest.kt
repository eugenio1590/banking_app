package com.app.data.source

import com.app.data.base.dao.TransactionDao
import com.app.data.base.entity.Transaction as Entity
import com.app.data.mapper.TransactionMapper
import com.app.data.source.transaction.TransactionDataSource
import com.app.domain.model.Account
import com.app.domain.model.Transaction
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.YearMonth
import java.util.Date

class TransactionDataSourceTest {

    private val transactionMapper = TransactionMapper()
    private val transactionDao: TransactionDao = mockk()
    private val transactionDataSource = TransactionDataSource(transactionMapper, transactionDao)

    @Test
    fun `should return a paginated transaction list`() = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 1000.0)
        val page = 1
        val limit = 10
        val entities = listOf(
            Entity(id = 1, type = "DEPOSIT", amount = 500.0, date = Date()),
            Entity(id = 2, type = "DEPOSIT", amount = 500.0, date = Date())
        )

        coEvery { transactionDao.findBy(account.id, limit, any<Int>()) }.returns(entities)

        // When
        val result = transactionDataSource.findBy(account, page, limit)

        // Then
        assertEquals(entities.size, result.size)
        coVerify {  transactionDao.findBy(account.id, limit, any<Int>()) }
    }

    @Test
    fun `should return all transactions made in a month of the year for a given account`() = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 500.0)
        val yearMonth = YearMonth.of(2023, 3)
        val entities = listOf(
            Entity(id = 1, type = "DEPOSIT", amount = 500.0, date = Date()),
            Entity(id = 2, type = "DEPOSIT", amount = 500.0, date = Date())
        )

        coEvery {  transactionDao.findBy(account.id, any<Date>(), any<Date>()) }.returns(entities)

        // When
        val result = transactionDataSource.findBy(account, yearMonth)

        // Then
        assertEquals(entities.size, result.size)
        coVerify {  transactionDao.findBy(account.id, any<Date>(), any<Date>()) }
    }

    @Test
    fun `should insert a new transaction and return true`() = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val transaction = Transaction(type = Transaction.Type.DEPOSIT, amount = 5.0, account = account)

        coJustRun { transactionDao.insert(any()) }

        // When
        val result = transactionDataSource.add(transaction)

        // Then
        assertTrue(result)
        coVerify { transactionDao.insert(any()) }
    }
}
