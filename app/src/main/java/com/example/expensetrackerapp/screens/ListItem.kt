package com.example.expensetrackerapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.db.ExpenseEntity
import com.example.expensetrackerapp.repository.ExtraVariables
import com.example.expensetrackerapp.viewModel.ClickState
import com.example.expensetrackerapp.viewModel.ExpenseViewModel

@Composable
fun ListItem(
    expenseEntity: ExpenseEntity,
    navController: NavController,
    viewModel: ExpenseViewModel
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate("addTransaction")
                ClickState.isItemClick = true
                viewModel.setClickedExpense(expenseEntity) // Set clicked expense in ViewModel
            }
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
        Column(Modifier.padding(horizontal = 8.dp)) {
            val color: Color = if (expenseEntity.incomeState) {
                Color(0xFF08D1EB)
            } else {
                Color(0xFFF86C6C)
            }
            Row(
                Modifier.padding(top = 6.dp)
            ) {
                Text(text = expenseEntity.note,
                    Modifier
                        .weight(1f)
                        .padding(bottom = 6.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color, shape = CircleShape)
                        .aspectRatio(1f)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = expenseEntity.amount.toString(),
                fontSize = 25.sp,
                color = Color(0xFFF86C6C)
            )
        }
    }
}