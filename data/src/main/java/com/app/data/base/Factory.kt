package com.app.data.base

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

internal object Factory {

    private const val DATABASE_NAME = "banking-database"
    fun create(context: Context): DataBase {
        return Room
            .databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Initial data
                    db.execSQL("INSERT INTO user(name) VALUES ('John Doe');")
                    db.execSQL("INSERT INTO account(type,balance,userId) values ('SAVING', 1000.0, 1)")
                    db.execSQL("INSERT INTO account(type,balance,userId) values ('SAVING', 670.0, 1)")
                    db.execSQL("INSERT INTO account(type,balance,userId) values ('CURRENT', 45.0, 1)")
                }
            })
            .build()
    }
}
