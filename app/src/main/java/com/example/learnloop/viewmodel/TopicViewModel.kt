package com.example.learnloop.viewmodel

import androidx.lifecycle.*
import com.example.learnloop.data.Topic
import com.example.learnloop.repository.TopicRepository
import com.example.learnloop.util.SpacedRepetitionUtils
import kotlinx.coroutines.launch
import java.time.LocalDate

class TopicViewModel(private val repository: TopicRepository) : ViewModel() {

    val allTopics: LiveData<List<Topic>> = repository.allTopics.asLiveData()

    // ✅ Replace Transformations.map with liveData builder
    val todayReviews: LiveData<List<Topic>> = allTopics.switchMap { list ->
        liveData {
            emit(list.filter { it.nextReviewDate <= LocalDate.now() })
        }
    }

    fun handleReviewFeedback(topic: Topic, feedback: String) {
        val updatedScore = SpacedRepetitionUtils.updateRetentionScore(topic.retentionScore, feedback)
        val updatedNextReview = SpacedRepetitionUtils.calculateNextReview(updatedScore, LocalDate.now())

        val updatedTopic = topic.copy(
            retentionScore = updatedScore,
            lastReviewed = LocalDate.now(),
            nextReviewDate = updatedNextReview
        )

        viewModelScope.launch {
            repository.updateTopic(updatedTopic)
        }
    }

    fun insertTopic(topic: Topic) = viewModelScope.launch {
        repository.insertTopic(topic)
    }

    fun deleteTopic(topic: Topic) = viewModelScope.launch {
        repository.deleteTopic(topic)
    }

    fun updateTopic(topic: Topic) = viewModelScope.launch {
        repository.updateTopic(topic)
    }
}
