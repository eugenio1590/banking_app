package com.app.data.source

import com.app.data.base.dao.AccountDao
import com.app.data.base.entity.Account as Entity
import com.app.data.mapper.AccountMapper
import com.app.data.source.account.AccountDataSource
import com.app.domain.model.Account
import com.app.domain.model.User
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AccountDataSourceTest {

    private val accountMapper = AccountMapper()
    private val accountDao = mockk<AccountDao>()
    private val accountDataSource = AccountDataSource(accountMapper, accountDao)

    @Test
    fun `should return a list of accounts for a given user`() = runBlocking {
        // Given
        val user = User(id = 1, name = "User")
        val entityList = listOf(Entity(id = 1, type = "SAVING", balance = 100.0))

        coEvery { accountDao.findBy(user.id) } returns entityList

        // When
        val result = accountDataSource.findBy(user)

        // Then
        assertEquals(entityList.size, result.size)
        coVerify { accountDao.findBy(user.id) }
    }

    @Test
    fun `should return true and update the account when successful`() = runBlocking {
        // Given
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 150.0)
        val entity = Entity(id = 1, type = "SAVING", balance = 100.0)

        coEvery { accountDao.getOne(account.id) } returns entity
        coJustRun { accountDao.update(any()) }

        // Given
        val result = accountDataSource.update(account)

        // Then
        assertTrue(result)
        coVerify { accountDao.getOne(account.id) }
        coVerify { accountDao.update(entity.copy(balance = account.balance)) }
    }

    @Test
    fun `should return false when account is not found in the data source`() = runBlocking {
        // When
        val account = Account(id = 1, type = Account.Type.SAVING, balance = 150.0)

        coEvery { accountDao.getOne(account.id) } returns null

        // When
        val result = accountDataSource.update(account)

        // Then
        assertFalse(result)
        coVerify { accountDao.getOne(account.id) }
        coVerify(exactly = 0) { accountDao.update(any()) }
    }

    // Add more test methods as needed...

}
