package com.example.learnloop.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.learnloop.data.Topic
import com.example.learnloop.databinding.DialogAddTopicBinding
import java.util.*

class AddTopicDialog(
    context: Context,
    private val onSave: (Topic) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogAddTopicBinding
    private var selectedExamDate: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddTopicBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.btnPickDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.inputName.text.toString().trim()
            val subject = binding.inputSubject.text.toString().trim()

            if (name.isNotEmpty() && subject.isNotEmpty()) {
                val topic = Topic(
                    name = name,
                    subject = subject,
                    examDate = selectedExamDate,
                    lastReviewed = System.currentTimeMillis(),
                    retentionScore = 100
                )
                onSave(topic)
                dismiss()
            } else {
                binding.inputName.error = "Required"
                binding.inputSubject.error = "Required"
            }
        }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                cal.set(year, month, day)
                selectedExamDate = cal.timeInMillis
                binding.txtDate.text = "${day}/${month + 1}/$year"
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
