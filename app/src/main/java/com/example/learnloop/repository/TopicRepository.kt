package com.example.learnloop.repository

import com.example.learnloop.data.Topic
import com.example.learnloop.data.TopicDao

class TopicRepository(private val topicDao: TopicDao) {
    val allTopics = topicDao.getAllTopics()

    suspend fun insert(topic: Topic) {
        topicDao.insert(topic)
    }
}
