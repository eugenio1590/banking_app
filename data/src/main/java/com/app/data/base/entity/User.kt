package com.app.data.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class User(
    @PrimaryKey val id: Int,
    val name: String
)
