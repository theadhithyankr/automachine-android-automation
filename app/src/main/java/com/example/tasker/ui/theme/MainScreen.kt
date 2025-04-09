package com.example.tasker.ui.theme

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tasker.viewmodel.MainViewModel
import java.util.*

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    selectedTime = String.format("%02d:%02d", hour, minute)
                    viewModel.createProfile(hour, minute, context)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }) {
            Text("Create Time-Based Notification")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (selectedTime.isNotEmpty()) {
            Text("Scheduled at: $selectedTime")
        }
    }
}
