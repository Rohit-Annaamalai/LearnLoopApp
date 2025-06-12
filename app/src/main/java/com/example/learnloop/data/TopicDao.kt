package com.example.learnloop.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topic: Topic)

    @Query("SELECT * FROM Topic ORDER BY examDate ASC")
    fun getAllTopics(): LiveData<List<Topic>>
}
