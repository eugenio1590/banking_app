package com.app.data.source.account

import com.app.data.base.dao.AccountDao
import com.app.data.mapper.AccountMapper
import com.app.domain.model.Account
import com.app.domain.model.User
import com.app.domain.repository.AccountRepository

/**
 * Internal class implementing the [AccountRepository] interface.
 *
 * This class serves as a data source for account-related operations, utilizing an [AccountMapper]
 * for mapping between data entities and domain models and an [AccountDao] for database interaction.
 *
 * @property accountMapper The mapper responsible for converting between account data entities and domain models.
 * @property accountDao The data access object (DAO) providing methods for interacting with the account database.
 */
internal class AccountDataSource(
    private val accountMapper: AccountMapper,
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun findBy(user: User): List<Account> {
        val entities = accountDao.findBy(user.id)
        return entities.map(accountMapper::toModel)
    }

    override suspend fun update(account: Account): Boolean {
        val entity = accountDao.getOne(account.id) ?: return false
        accountDao.update(entity.copy(balance = account.balance))
        return true
    }
}
