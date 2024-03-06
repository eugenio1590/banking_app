package com.app.domain.interactor.account

import com.app.domain.interactor.account.search.SearchAccounts
import com.app.domain.interactor.account.search.SearchAccountsUseCase
import com.app.domain.model.Account
import com.app.domain.model.User
import com.app.domain.repository.AccountRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchAccountsTest {

    private val accountRepository: AccountRepository = mockk()
    private val searchAccounts: SearchAccounts = SearchAccountsUseCase(accountRepository)

    private val user = User(id = 1, name = "Person")

    @Test
    fun `when the use case is invoked should return expected list of accounts`(): Unit = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 45.0)
        coEvery { accountRepository.findBy(user) }.returns(listOf(account))

        // When
        val accounts = searchAccounts(user = user)

        // Then
        assertEquals(1, accounts.size)
        coVerify { accountRepository.findBy(user) }
    }

    @Test(expected = Error::class)
    fun `when the use case is invoked should throw an exception on failure`(): Unit = runBlocking {
        // Given
        coEvery { accountRepository.findBy(user) }.throws(Error("Unexpected error"))

        // When
        searchAccounts(user = user)
    }
}
