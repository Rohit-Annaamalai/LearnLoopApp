package com.example.learnloop.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learnloop.data.AppDatabase
import com.example.learnloop.data.Topic
import com.example.learnloop.repository.TopicRepository
import com.example.learnloop.ui.screens.DailyReviewScreen
import com.example.learnloop.ui.theme.LearnLoopTheme
import com.example.learnloop.viewmodel.TopicViewModel
import com.example.learnloop.viewmodel.TopicViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var topicViewModel: TopicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = TopicRepository(db.topicDao())
        topicViewModel = ViewModelProvider(this, TopicViewModelFactory(repository))
            .get(TopicViewModel::class.java)

        setContent {
            LearnLoopTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            TopicScreen(topicViewModel, navController)
                        }
                        composable("review") {
                            DailyReviewScreen(topicViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopicScreen(viewModel: TopicViewModel, navController: NavController) {
    val allTopics by viewModel.allTopics.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Topic")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Your Topics", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("review") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Review Today’s Topics")
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(allTopics) { topic ->
                    TopicItem(topic)
                }
            }

            if (showDialog) {
                AddTopicDialog(
                    onDismiss = { showDialog = false },
                    onSave = { newTopic ->
                        viewModel.insertTopic(newTopic)
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun TopicItem(topic: Topic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = topic.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Subject: ${topic.subject}")
            Text(text = "Next Review: ${topic.nextReviewDate}")
            Text(text = "Retention Score: ${"%.1f".format(topic.retentionScore)}")
        }
    }
}
