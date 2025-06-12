package com.example.learnloop.util

import java.time.LocalDate

object SpacedRepetitionUtils {

    fun calculateNextReview(score: Float, lastReviewed: LocalDate): LocalDate {
        val daysToAdd = when {
            score >= 4.5f -> 7
            score >= 3.5f -> 3
            score >= 2.5f -> 2
            else -> 1
        }
        return lastReviewed.plusDays(daysToAdd.toLong())
    }

    fun updateRetentionScore(current: Float, feedback: String): Float {
        return when (feedback.lowercase()) {
            "easy" -> (current + 0.8f).coerceAtMost(5.0f)
            "okay" -> (current + 0.3f).coerceAtMost(5.0f)
            "hard" -> (current - 0.5f).coerceAtLeast(0.0f)
            "forgot" -> 1.0f
            else -> current
        }
    }
}
