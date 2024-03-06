package com.app.data.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
internal data class Transaction(
    @PrimaryKey val id: Int = -1,
    val type: String,
    val amount: Double,
    val date: Date,
    val accountId: Int? = -1
)
