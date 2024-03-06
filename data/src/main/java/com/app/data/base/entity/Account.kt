package com.app.data.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class Account(
    @PrimaryKey val id: Int,
    val type: String,
    val balance: Double,
    val userId: Int
)
