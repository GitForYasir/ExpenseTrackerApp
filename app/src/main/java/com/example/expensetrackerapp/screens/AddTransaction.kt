package com.example.expensetrackerapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.expensetrackerapp.db.ExpenseEntity
import com.example.expensetrackerapp.repository.ExtraVariables
import com.example.expensetrackerapp.viewModel.ClickState
import com.example.expensetrackerapp.viewModel.ExpenseViewModel

@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: ExpenseViewModel
) {
    val isItemClick by remember {
        mutableStateOf(ClickState.isItemClick)
    }
    val clickedExpenseEntity by remember {
        mutableStateOf(viewModel.getClickedExpense())
    }

    // Initialize fields with corresponding data if available
    var amount by remember {
        mutableStateOf(clickedExpenseEntity?.amount.toString() ?: "")
    }
    var note by remember {
        mutableStateOf(clickedExpenseEntity?.note ?: "")
    }
    var expenseState by remember {
        mutableStateOf(clickedExpenseEntity?.expenseState ?: false)
    }
    var incomeState by remember {
        mutableStateOf(clickedExpenseEntity?.incomeState ?: false)
    }
    var income by remember {
        mutableStateOf(clickedExpenseEntity?.income.toString() ?: "")
    }
    var expense by remember {
        mutableStateOf(clickedExpenseEntity?.expense.toString() ?: "")
    }
    var balance by remember {
        mutableIntStateOf(clickedExpenseEntity?.balance ?: 0)
    }


//    var amount by remember {
//        mutableStateOf("")
//    }
//    var note by remember {
//        mutableStateOf("")
//    }
//    var expenseState by remember {
//        mutableStateOf(false)
//    }
//    var incomeState by remember {
//        mutableStateOf(false)
//    }
//    var income by remember {
//        mutableStateOf("")
//    }
//    var expense by remember {
//        mutableStateOf("")
//    }
//    var balance by remember {
//        mutableIntStateOf(0)
//    }

    // Reset the clicked expense in the ViewModel after retrieving the data
    viewModel.setClickedExpense(null)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xFFF86C6C)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add Transaction",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = amount,
            onValueChange = {
                            amount = it
            },
            label = { Text(text = "Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        OutlinedTextField(
            value = note,
            onValueChange = { newNote ->
                            note = newNote
            },
            Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 3.dp),
            label = { Text(text = "Note") },
        )

        Row(
            Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
        ) {
            Checkbox(
                checked = expenseState,
                onCheckedChange = { isChecked ->
                    expenseState = isChecked
                    incomeState = !isChecked
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF08D1EB),
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        expenseState = false
                    }
            )

            Text(
                text = "Expense",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            Checkbox(
                checked = incomeState,
                onCheckedChange = { isChecked ->
                    incomeState = isChecked
                    expenseState = !isChecked
                },
                colors = CheckboxDefaults.colors(Color(0xFF08D1EB)),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        expenseState = false
                    }
            )

            Text(
                text = "Income",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }

        if (isItemClick) {
            if (clickedExpenseEntity != null) {
                Button(
                    onClick = {
                        viewModel.updateExpense(
                            clickedExpenseEntity!!.copy(
                                income = income.toInt(),
                                expense = expense.toInt(),
                                balance = balance,
                                amount = amount.toInt(),
                                note = note,
                                incomeState = incomeState,
                                expenseState = expenseState
                            )
                        )
                        viewModel.setClickedExpense(clickedExpenseEntity)
                        navController.navigate("dashboard")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFF86C6C))
                ) {
                    Text(text = "UPDATE")
                }

                Button(
                    onClick = {
                        viewModel.deleteExpense(
                            clickedExpenseEntity!!
                        )
                        navController.navigate("dashboard")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFF86C6C))
                ) {
                    Text(text = "DELETE")
                }
            }

        } else {
            Button(
                onClick = {
                    if (amount.isNotEmpty() && note.isNotEmpty() && (incomeState || expenseState)) {
                        if (amount.isDigitsOnly()) {
                            val amountValue = amount.toInt()
                            if (incomeState) {
                                income += amount.toInt()
                            } else {
                                expense += amount.toInt()
                            }
                            // Validate income and expense strings before converting to integers
                            val incomeValue = income.toIntOrNull() ?: 0
                            val expenseValue = expense.toIntOrNull() ?: 0
                            balance = incomeValue - expenseValue

                            viewModel.insertExpense(
                                ExpenseEntity(
                                    income = incomeValue,
                                    expense = expenseValue,
                                    balance = balance,
                                    amount = amountValue,
                                    note = note,
                                    incomeState = incomeState,
                                    expenseState = expenseState
                                )
                            )
                        }
                        navController.navigate("dashboard")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFF86C6C))
            ) {
                Text(text = "ADD")
            }
        }

    }
}