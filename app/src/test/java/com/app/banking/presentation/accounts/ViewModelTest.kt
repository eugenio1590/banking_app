package com.app.banking.presentation.accounts

import com.app.banking.presentation.config.MainDispatcherRule
import com.app.domain.interactor.account.search.SearchAccounts
import com.app.domain.interactor.transaction.make.MakeTransaction
import com.app.domain.model.Account
import com.app.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest {

    private val searchAccounts: SearchAccounts = mockk()
    private val makeTransaction: MakeTransaction = mockk()
    private lateinit var viewModel: ViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ViewModel(searchAccounts, makeTransaction)
    }

    @Test
    fun `should indicate the first initial state`() {
        assertEquals(UiState(), viewModel.uiState.value)
    }

    @Test
    fun `should handle the intent to load user's accounts`() = runTest {
        // Given
        val user = User(id = 123, name = "John Doe")
        val accounts = listOf(Account(id = 1, type = Account.Type.SAVING, balance = 100.0))

        coEvery {  searchAccounts(user) }.returns(accounts)

        // When
        viewModel.handle(ViewIntent.LoadAccounts(user))

        // Then
        val state = viewModel.uiState.value.loadState
        assertTrue(state is LoadState.Success)
        assertEquals(accounts, (state as LoadState.Success).accounts)
        coVerify {  searchAccounts(user) }
    }

    @Test
    fun `should handle the intent to perform a financial transaction`() = runTest {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, 100.0)
        val amount = 50.0
        val updatedAccount = account.copy(balance = account.balance + amount)

        coEvery {  makeTransaction.deposit(account, amount) }.returns(updatedAccount)
        viewModel.update(UiState().copy(loadState = LoadState.Success(listOf(account))))

        // When
        viewModel.handle(ViewIntent.Deposit(account, amount))

        // Then
        val transactionState = viewModel.uiState.value.transactionState
        assertTrue(transactionState is TransactionState.Success)

        val loadState = viewModel.uiState.value.loadState
        assertTrue(loadState is LoadState.Success)
        val accounts = (loadState as LoadState.Success).accounts
        assertEquals(updatedAccount, accounts.first { it.id == updatedAccount.id })

        coVerify {  makeTransaction.deposit(account, amount) }
    }
}
