package com.safarayfon.androidtodo.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddGroupDialog(
  onDismiss: () -> Unit, onConfirm: (String) -> Unit
) {
  var groupName by remember { mutableStateOf("") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large
    ) {
      Column(
        modifier = Modifier.padding(all = 24.dp)
      ) {
        Text(
          text = "Add new group",
          style = MaterialTheme.typography.headlineSmall,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
          value = groupName,
          onValueChange = { groupName = it },
          label = { Text("Group name") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
          TextButton(onClick = onDismiss) {
            Text("Cancel")
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            onClick = {
              onConfirm(groupName)
              onDismiss()
            }, enabled = groupName.isNotBlank()
          ) {
            Text("Add")
          }
        }
      }
    }
  }
}