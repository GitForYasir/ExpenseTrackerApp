package com.example.expensetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.expensetrackerapp.db.ExpenseDatabase
import com.example.expensetrackerapp.repository.ExpenseRepoImpl
import com.example.expensetrackerapp.screens.AppNavigation
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme
import com.example.expensetrackerapp.viewModel.ExpenseViewModel
import com.example.expensetrackerapp.viewModel.ExpenseViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory(ExpenseRepoImpl(ExpenseDatabase.getDatabase(this)))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerAppTheme {
                    AppNavigation(viewModel)
            }
        }
    }
}