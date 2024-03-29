package com.app.data.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.data.base.converter.DateConverter
import com.app.data.base.dao.AccountDao
import com.app.data.base.dao.TransactionDao
import com.app.data.base.dao.UserDao
import com.app.data.base.entity.Account
import com.app.data.base.entity.Transaction
import com.app.data.base.entity.User

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        User::class,
        Account::class,
        Transaction::class
    ]
)
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}
