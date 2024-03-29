package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.db.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepo {
    suspend fun getExpense(): Flow<List<ExpenseEntity>>
    suspend fun insertExpense(expenseEntity: ExpenseEntity)
    suspend fun updateExpense(expenseEntity: ExpenseEntity)
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

}

// we can create repe and repo impl in one class like the following

//class random(database: ExpenseDatabase){
//    private val db = database.expenseDao
//    suspend fun get(): Flow<List<ExpenseEntity>> = db.getExpense()
//}