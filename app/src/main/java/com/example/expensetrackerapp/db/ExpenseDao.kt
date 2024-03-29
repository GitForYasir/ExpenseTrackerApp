package com.example.expensetrackerapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenseentity")
    fun getExpense(): Flow<List<ExpenseEntity>>


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertExpense2(expenseEntity2: ExpenseEntity2)
//
//    @Update
//    suspend fun updateExpense2(expenseEntity2: ExpenseEntity2)
//
//    @Delete
//    suspend fun deleteExpense2(expenseEntity2: ExpenseEntity2)
//
//    @Query("SELECT * FROM expenseentity2")
//    fun getExpense2(): Flow<List<ExpenseEntity2>>
}