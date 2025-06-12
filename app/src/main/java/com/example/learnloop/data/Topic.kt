package com.example.learnloop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val subject: String,
    val examDate: Long,
    val lastReviewed: Long,
    val retentionScore: Int
)
