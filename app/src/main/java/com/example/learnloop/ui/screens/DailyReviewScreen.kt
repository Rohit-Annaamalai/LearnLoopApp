package com.example.learnloop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learnloop.data.Topic
import com.example.learnloop.viewmodel.TopicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReviewScreen(viewModel: TopicViewModel) {
    val todayTopics = viewModel.todayReviews.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Today's Review") })
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            if (todayTopics.value.isEmpty()) {
                Text("No topics due for today!", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    items(todayTopics.value) { topic ->
                        TopicReviewItem(topic) { feedback ->
                            viewModel.handleReviewFeedback(topic, feedback)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopicReviewItem(topic: Topic, onFeedback: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(topic.name, style = MaterialTheme.typography.titleLarge)
            Text("Subject: ${topic.subject}")
            Text("Exam Date: ${topic.examDate}")
            Text("Retention: %.1f".format(topic.retentionScore))

            Row(Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { onFeedback("easy") }) { Text("Easy") }
                Button(onClick = { onFeedback("medium") }) { Text("Medium") }
                Button(onClick = { onFeedback("hard") }) { Text("Hard") }
            }
        }
    }
}
