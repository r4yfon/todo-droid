package com.safarayfon.androidtodo.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.safarayfon.androidtodo.data.entities.TodoItem
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    item: TodoItem,
    onBack: () -> Unit,
    onDelete: (TodoItem) -> Unit,
    onUpdate: (TodoItem, () -> Unit) -> Unit,
    onQuickUpdate: (TodoItem) -> Unit


) {
    var title by remember { mutableStateOf(item.title) }
    var description by remember { mutableStateOf(item.details) }
    var isCompleted by remember { mutableStateOf(item.isCompleted) }
    val formattedDate = remember(item.creationDate) {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(item.creationDate)
    }
    var isDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Created: $formattedDate", style = MaterialTheme.typography.bodySmall)

                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Details") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isCompleted,
                            onCheckedChange = {
                                isCompleted = it
                                onQuickUpdate(
                                    item.copy(
                                        title = title,
                                        details = description,
                                        isCompleted = it
                                    )
                                )

                            }
                        )
                        Text(text = if (isCompleted) "Completed" else "Not Completed")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                if (isDeleteConfirmation) {
                                    onDelete(item)
                                    onBack()
                                } else {
                                    isDeleteConfirmation = true
                                }
                            },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = if (isDeleteConfirmation)
                                    MaterialTheme.colorScheme.error  // Red background
                                else
                                    Color.Transparent,
                                contentColor = if (isDeleteConfirmation)
                                    MaterialTheme.colorScheme.onError  // White text
                                else
                                    MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text(
                                text = if (isDeleteConfirmation) "Confirm Delete" else "Delete",
                                color = if (isDeleteConfirmation)
                                    MaterialTheme.colorScheme.onError  // White text for confirm state
                                else
                                    MaterialTheme.colorScheme.error  // Red text for normal state
                            )
                        }

                        Button(
                            onClick = {
                                onUpdate(
                                    TodoItem(
                                        id = item.id,
                                        groupId = item.groupId,
                                        title = title,
                                        details = description,
                                        isCompleted = isCompleted,
                                        creationDate = item.creationDate
                                    )
                                ) {
                                    onBack()
                                }
                            },
                            enabled = title.isNotBlank()
                        ) {
                            Text("Save")
                        }

                    }
                }
            }
        }
    }

    LaunchedEffect(isDeleteConfirmation) {
        if (isDeleteConfirmation) {
            kotlinx.coroutines.delay(2000)
            isDeleteConfirmation = false
        }
    }
}
