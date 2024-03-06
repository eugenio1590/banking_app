package com.app.data.base.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.app.data.base.entity.Account

@Dao
internal interface AccountDao {
    @Query("SELECT * FROM account WHERE userId = :userId")
    suspend fun findBy(userId: Int): List<Account>

    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getOne(id: Int): Account?

    @Update
    suspend fun update(vararg account: Account)
}
