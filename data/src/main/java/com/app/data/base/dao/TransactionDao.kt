package com.app.data.base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.data.base.entity.Transaction
import java.util.Date

@Dao
internal interface TransactionDao {
    @Query("SELECT * FROM `transaction` WHERE accountId = :accountId LIMIT :limit OFFSET :offset")
    fun findBy(accountId: Int, limit: Int, offset: Int): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE accountId = :accountId AND date BETWEEN :start AND :end")
    fun findBy(accountId: Int, start: Date, end: Date): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)
}
