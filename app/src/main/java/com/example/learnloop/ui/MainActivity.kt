package com.example.learnloop.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnloop.data.Topic
import com.example.learnloop.databinding.ActivityMainBinding
import com.example.learnloop.viewmodel.TopicViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val topicViewModel: TopicViewModel by viewModels()
    private val adapter = TopicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        topicViewModel.allTopics.observe(this) {
            adapter.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            val dialog = AddTopicDialog(this) { topic ->
                topicViewModel.insert(topic)
            }
            dialog.show()
        }
    }
}
