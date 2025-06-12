package com.example.learnloop.data

import androidx.room.*

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topic: Topic)

    @Update
    suspend fun update(topic: Topic)

    @Delete
    suspend fun delete(topic: Topic)

    @Query("SELECT * FROM topics ORDER BY examDate ASC")
    fun getAllTopics(): kotlinx.coroutines.flow.Flow<List<Topic>>
}
