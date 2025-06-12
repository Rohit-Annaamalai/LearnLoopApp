package com.example.learnloop.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.learnloop.data.AppDatabase
import com.example.learnloop.data.Topic
import com.example.learnloop.repository.TopicRepository
import kotlinx.coroutines.launch

class TopicViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TopicRepository
    val allTopics: LiveData<List<Topic>>

    init {
        val dao = AppDatabase.getDatabase(application).topicDao()
        repository = TopicRepository(dao)
        allTopics = repository.allTopics
    }

    fun insert(topic: Topic) = viewModelScope.launch {
        repository.insert(topic)
    }
}
