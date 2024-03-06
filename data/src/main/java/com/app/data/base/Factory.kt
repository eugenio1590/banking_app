package com.app.data.base

import android.content.Context
import androidx.room.Room

internal object Factory {

    private const val DATABASE_NAME = "banking-database"
    fun create(context: Context): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME).build()
    }
}
