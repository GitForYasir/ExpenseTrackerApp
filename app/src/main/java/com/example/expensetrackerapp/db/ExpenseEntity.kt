package com.example.expensetrackerapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseEntity(
    val income: Int,
    val expense: Int,
    val balance: Int,
    val amount: Int,
    val note: String,
    var incomeState: Boolean,
    var expenseState: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
