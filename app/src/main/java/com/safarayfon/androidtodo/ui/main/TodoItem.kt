package com.safarayfon.androidtodo.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.safarayfon.androidtodo.data.entities.TodoItem
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun TodoItem(
  item: TodoItem,
  isChecked: Boolean,
  onCheckedChange: (TodoItem, Boolean) -> Unit = {
    _, _ ->  },
  onItemDoubleTap: (TodoItem) -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 4.dp)
      .pointerInput(item.id) {
        detectTapGestures(
          onDoubleTap = {
            onItemDoubleTap(item)
          }
        )
      }
  ) {
    Row {
      Checkbox(
        checked = isChecked,
        onCheckedChange = { newChecked ->
          onCheckedChange(item, newChecked)
        },
        modifier = Modifier.width(48.dp))
      Text(
        text = item.title,
        modifier = Modifier.align(Alignment.CenterVertically),
        style = MaterialTheme.typography.bodyLarge,
        textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
      )
    }
  }
}