package com.example.expensetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.db.ExpenseEntity
import com.example.expensetrackerapp.repository.ExpenseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repo: ExpenseRepo): ViewModel() {
    private val _expense: MutableStateFlow<List<ExpenseEntity>> = MutableStateFlow(emptyList())
    val expenses = _expense.asStateFlow()

    private var clickedExpense: ExpenseEntity? = null

    fun getClickedExpense(): ExpenseEntity? {
        return clickedExpense
    }

    fun setClickedExpense(expenseEntity: ExpenseEntity?) {
        clickedExpense = expenseEntity
    }

    init {
        getExpense()
    }

    private fun getExpense(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getExpense().collect{data->
                _expense.update { data }
            }
        }
    }

    fun insertExpense(expenseEntity: ExpenseEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertExpense(expenseEntity)
        }
    }

    fun updateExpense(expenseEntity: ExpenseEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateExpense(expenseEntity)
        }
    }

    fun deleteExpense(expenseEntity: ExpenseEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteExpense(expenseEntity)
        }
    }
}

class ExpenseViewModelFactory(private val repo: ExpenseRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            return ExpenseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
