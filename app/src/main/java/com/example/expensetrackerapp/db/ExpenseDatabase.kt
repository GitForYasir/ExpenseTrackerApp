package com.example.expensetrackerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val expenseDao: ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDatabase::class.java,
                        "expenseDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }
}