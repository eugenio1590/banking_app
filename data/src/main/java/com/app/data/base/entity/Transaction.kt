package com.app.data.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class Transaction(
    @PrimaryKey val id: Int,
    val type: String,
    val amount: Double,
    val accountId: Int
)
