package com.app.banking.di

import com.app.domain.interactor.account.search.SearchAccounts
import com.app.domain.interactor.transaction.make.MakeTransaction
import com.app.domain.interactor.transaction.search.SearchTransactions
import com.app.domain.repository.AccountRepository
import com.app.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideSearchAccounts(accountRepository: AccountRepository): SearchAccounts {
        return SearchAccounts.Factory.create(accountRepository)
    }

    @Provides
    fun provideSearchTransactions(transactionRepository: TransactionRepository): SearchTransactions {
        return SearchTransactions.Factory.create(transactionRepository)
    }

    @Provides
    fun provideMakeTransaction(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): MakeTransaction {
        return MakeTransaction.Factory.create(transactionRepository, accountRepository)
    }
}