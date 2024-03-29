package com.example.expensetrackerapp.repository

import com.example.expensetrackerapp.db.ExpenseDatabase
import com.example.expensetrackerapp.db.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepoImpl(database: ExpenseDatabase): ExpenseRepo{
    private val dao = database.expenseDao

    override suspend fun getExpense(): Flow<List<ExpenseEntity>> = dao.getExpense()
    override suspend fun insertExpense(expenseEntity: ExpenseEntity) = dao.insertExpense(expenseEntity)
    override suspend fun updateExpense(expenseEntity: ExpenseEntity) = dao.updateExpense(expenseEntity)
    override suspend fun deleteExpense(expenseEntity: ExpenseEntity) = dao.deleteExpense(expenseEntity)

}