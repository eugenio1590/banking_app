package com.app.domain.interactor.transaction

import com.app.domain.exception.Error
import com.app.domain.interactor.transaction.make.MakeTransaction
import com.app.domain.interactor.transaction.make.MakeTransactionUseCase
import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.AccountRepository
import com.app.domain.repository.TransactionRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MakeTransactionTest {

    private val transactionRepository: TransactionRepository = mockk()
    private val accountRepository: AccountRepository = mockk()
    private val makeTransaction: MakeTransaction =
        MakeTransactionUseCase(transactionRepository, accountRepository)

    @Test
    fun `deposit should perform a valid deposit transaction`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val amount = 50.0

        coEvery { transactionRepository.add(any()) } returns true
        coEvery { accountRepository.update(any()) } returns true

        // When
        val result = makeTransaction.deposit(account, amount)

        // Then
        assertEquals(Transaction.Type.DEPOSIT, result.type)
        assertEquals(account.balance + amount, result.balance)
        //assertEquals(account.balance + amount, result.updatedAccount?.balance)
    }

    @Test(expected = Error.TransactionError.NonPositiveAmountError::class)
    fun `deposit should throw an error for non-positive amount`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val amount = 0.0

        // When
        makeTransaction.deposit(account, amount)
    }

    @Test
    fun `withdrawal should perform a valid withdrawal transaction`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val amount = 50.0

        coEvery { transactionRepository.add(any()) } returns true
        coEvery { accountRepository.update(any()) } returns true

        // When
        val result = makeTransaction.withdrawal(account, amount)

        // Then
        assertEquals(Transaction.Type.WITHDRAWAL, result.type)
        assertEquals(account.balance - amount, result.balance)
        //assertEquals(account.balance - amount, result.updatedAccount?.balance)
    }

    @Test(expected = Error.TransactionError.NonPositiveAmountError::class)
    fun `withdrawal should throw an error for non-positive amount`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val amount = 0.0

        // Then
        makeTransaction.withdrawal(account, amount)
    }

    @Test(expected = Error.TransactionError.ZeroBalanceError::class)
    fun `withdrawal should throw an error for zero balance`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 0.0)
        val amount = 50.0

        // Then
        makeTransaction.withdrawal(account, amount)
    }

    @Test(expected = Error.TransactionError.InsufficientBalanceError::class)
    fun `withdrawal should throw an error for insufficient balance`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 30.0)
        val amount = 50.0

        // Then
        makeTransaction.withdrawal(account, amount)
    }

    @Test(expected = Error.TransactionError.TransactionProcessingError::class)
    fun `withdrawal should throw an error when repository update fails`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 100.0)
        val amount = 50.0

        coEvery { transactionRepository.add(any()) } returns true
        coEvery { accountRepository.update(any()) } returns false

        // Then
        makeTransaction.withdrawal(account, amount)
    }
}
