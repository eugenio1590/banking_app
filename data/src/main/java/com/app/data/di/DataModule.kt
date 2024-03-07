package com.app.data.di

import android.content.Context
import com.app.data.base.DataBase
import com.app.data.base.Factory
import com.app.data.source.account.AccountDataSource
import com.app.data.source.transaction.TransactionDataSource
import com.app.domain.repository.AccountRepository
import com.app.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): DataBase {
        return Factory.create(context)
    }

    @Provides
    fun provideAccountRepository(dataBase: DataBase): AccountRepository {
        return AccountDataSource(accountDao = dataBase.accountDao())
    }

    @Provides
    fun provideTransactionRepository(dataBase: DataBase): TransactionRepository {
        return TransactionDataSource(transactionDao = dataBase.transactionDao())
    }
}
