package com.example.learnloop.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.learnloop.data.Topic
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Composable
fun AddTopicDialog(
    onDismiss: () -> Unit,
    onSave: (Topic) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var examDate by remember { mutableStateOf<LocalDate?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        showDatePickerDialog(context) { millis ->
            examDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            showDatePicker.value = false
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Topic") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Topic Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showDatePicker.value = true }) {
                    Text(text = examDate?.toString() ?: "Pick Exam Date")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && subject.isNotBlank() && examDate != null) {
                        val today = LocalDate.now()
                        val newTopic = Topic(
                            name = name,
                            subject = subject,
                            examDate = examDate!!,
                            lastReviewed = today,
                            nextReviewDate = today,
                            retentionScore = 3.0f
                        )
                        onSave(newTopic)
                        onDismiss()
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun showDatePickerDialog(context: Context, onDateSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val pickedCalendar = Calendar.getInstance().apply {
                set(year, month, day)
            }
            onDateSelected(pickedCalendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
