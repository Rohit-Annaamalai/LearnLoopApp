package com.example.learnloop.repository

import com.example.learnloop.data.Topic
import com.example.learnloop.data.TopicDao
import kotlinx.coroutines.flow.Flow

class TopicRepository(private val topicDao: TopicDao) {

    val allTopics: Flow<List<Topic>> = topicDao.getAllTopics()

    suspend fun insertTopic(topic: Topic) {
        topicDao.insert(topic)
    }

    suspend fun deleteTopic(topic: Topic) {
        topicDao.delete(topic)
    }

    // ✅ Add this if missing
    suspend fun updateTopic(topic: Topic) {
        topicDao.update(topic)
    }
}
