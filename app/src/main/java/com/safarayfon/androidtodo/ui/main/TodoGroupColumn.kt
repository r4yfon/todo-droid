package com.safarayfon.androidtodo.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import com.safarayfon.androidtodo.data.entities.TodoGroup
import com.safarayfon.androidtodo.data.entities.TodoItem

@Composable
fun TodoGroupColumn(
  group: TodoGroup,
  items: List<TodoItem>,
  modifier: Modifier = Modifier,
  onAddItemClick: (Long) -> Unit,
  onItemCheckedChange: (TodoItem, Boolean) -> Unit = { _, _ -> },
  onDeleteGroupClick: (TodoGroup) -> Unit = {},
  onItemDoubleTap: (TodoItem) -> Unit = {}

) {
  val itemsState = remember {
    mutableStateMapOf<Long, Boolean>().apply {
        items.forEach { put(it.id, it.isCompleted) }
      }
  }

  var isDeleteConfirmation by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .width(200.dp)
      .padding(horizontal = 4.dp)
      .background(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        shape = MaterialTheme.shapes.medium)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
      text = group.name,
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 12.dp),
      fontSize = 18.sp
      )
      Button(
        onClick = {
          if (isDeleteConfirmation) {
            onDeleteGroupClick(group)
            isDeleteConfirmation = false
          } else {
            isDeleteConfirmation = true
          }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(end = 12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isDeleteConfirmation)
            MaterialTheme.colorScheme.error
          else
            MaterialTheme.colorScheme.errorContainer,
          contentColor = if (isDeleteConfirmation)
            MaterialTheme.colorScheme.onError
          else
            MaterialTheme.colorScheme.onErrorContainer
        )) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Delete Group",
//          modifier = Modifier
//            .padding(horizontal = 4.dp)
        )
      }
    }

    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

    Column(modifier = Modifier
      .padding(4.dp).weight(1f)
      .verticalScroll(rememberScrollState())) {
      items.forEach { item ->
        TodoItem(
          item = item,
          isChecked = itemsState[item.id] ?: item.isCompleted,
          onCheckedChange = { todoItem, isChecked ->
            itemsState[todoItem.id] = isChecked
            onItemCheckedChange(todoItem, isChecked)
          },
          onItemDoubleTap = { todoItem ->
            onItemDoubleTap(todoItem)
          }
        )
      }
    }
    HorizontalDivider()

    Button(
      onClick = {onAddItemClick(group.id)},
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 12.dp),
      shape = MaterialTheme.shapes.small
    ) {
      Icon(Icons.Default.Add, contentDescription = "Add New Item")
      Spacer(modifier = Modifier.width(4.dp))
      Text("Add Item")
    }
  }

  LaunchedEffect(isDeleteConfirmation) {
    if (isDeleteConfirmation) {
      delay(2000)
      isDeleteConfirmation = false
    }
  }
}
