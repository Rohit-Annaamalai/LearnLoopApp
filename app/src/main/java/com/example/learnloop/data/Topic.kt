package com.example.learnloop.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "topics")
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val subject: String,
    val examDate: LocalDate,
    val retentionScore: Float = 3.0f,
    val lastReviewed: LocalDate = LocalDate.now(),
    val nextReviewDate: LocalDate = LocalDate.now().plusDays(1)
)
