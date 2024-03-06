package com.app.domain.interactor.account.search

import com.app.domain.model.Account
import com.app.domain.model.User
import com.app.domain.repository.AccountRepository

/**
 * Use case for searching and retrieving accounts.
 *
 * This internal class implements the [SearchAccounts] interface, providing a higher-level abstraction
 * for searching accounts. It delegates the actual search operation to the [AccountRepository].
 *
 * @param accountRepository The repository responsible for fetching accounts from a data source.
 */
internal class SearchAccountsUseCase(
    private val accountRepository: AccountRepository
) : SearchAccounts {
    override suspend fun invoke(user: User): List<Account> = accountRepository.findBy(user)
}
