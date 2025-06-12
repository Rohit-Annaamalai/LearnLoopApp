package com.example.learnloop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnloop.data.Topic
import com.example.learnloop.databinding.ItemTopicBinding

class TopicAdapter : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private var topicList = listOf<Topic>()

    fun submitList(list: List<Topic>) {
        topicList = list
        notifyDataSetChanged()
    }

    inner class TopicViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topicList[position]
        holder.binding.topicName.text = topic.name
        holder.binding.topicSubject.text = topic.subject
    }

    override fun getItemCount() = topicList.size
}
