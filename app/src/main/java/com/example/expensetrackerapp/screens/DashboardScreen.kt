package com.example.expensetrackerapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.viewModel.ExpenseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerapp.viewModel.ClickState


@Composable
fun AppNavigation(viewModel: ExpenseViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }
        composable("addTransaction") {
            AddTransactionScreen(navController, viewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: ExpenseViewModel
) {
    val expenses by viewModel.expenses.collectAsState(emptyList())
    val totalIncome = expenses.filter { !it.expenseState }.sumOf { it.amount }
    val totalExpense = expenses.filter { it.expenseState }.sumOf { it.amount }
    val balance = totalIncome - totalExpense

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addTransaction")
                    ClickState.isItemClick = false
                },
                shape = RoundedCornerShape(50.dp),
                contentColor = Color.White,
                containerColor = Color(0xFFF86C6C)
            ) {
                Row(Modifier.padding(8.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
                    Text(text = "Add Transaction")
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color(0xFFF86C6C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Dashboard",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 8.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = true,
                            Color.Black
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row {
                            Text(text = "Income", modifier = Modifier.weight(2f), fontSize = 18.sp)
                            Text(
                                text = "$totalIncome",
                                Modifier.weight(2f),
                                textAlign = TextAlign.End,
                                color = Color(0xFF08D1EB),
                                fontSize = 18.sp
                            )
                        }
                        Row {
                            Text(text = "Expense", modifier = Modifier.weight(2f), fontSize = 18.sp)
                            Text(
                                text = "$totalExpense",
                                Modifier.weight(2f),
                                textAlign = TextAlign.End,
                                color = Color(0xFFF86C6C),
                                fontSize = 18.sp
                            )
                        }
                        Row {
                            Text(text = "Balance", modifier = Modifier.weight(2f), fontSize = 18.sp)
                            Text(
                                text = "$balance", Modifier.weight(2f), textAlign = TextAlign.End,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    items(expenses) { expense ->
                        ListItem(expense, navController, viewModel)
                    }
                }
            }
        }
    }
}