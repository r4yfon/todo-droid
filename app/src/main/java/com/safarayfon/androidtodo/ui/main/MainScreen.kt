package com.safarayfon.androidtodo.ui.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.safarayfon.androidtodo.data.entities.TodoGroup
import com.safarayfon.androidtodo.data.entities.TodoItem


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
  var showAddGroupDialog by remember { mutableStateOf(false) }
  var addItemToGroup by remember { mutableStateOf<Long?>(null) }
  val groups = mainViewModel.groupsWithItems.value

  var selectedItemIdForDetails by remember { mutableStateOf<Long?>(null) }

  val selectedItemFlow = selectedItemIdForDetails?.let { mainViewModel.getItemFlowById(it) }
  val selectedItem by selectedItemFlow?.collectAsStateWithLifecycle(initialValue = null) ?: remember { mutableStateOf(null) }



  selectedItem?.let { item ->
    TodoDetailScreen(
      item = item,
      onBack = { selectedItemIdForDetails = null },
      onDelete = {
        mainViewModel.deleteItem(it)
        selectedItemIdForDetails = null
      },
      onUpdate = { updatedItem, onFinished ->
        mainViewModel.updateItem(updatedItem) {
          onFinished()
          selectedItemIdForDetails = null
        }
      },
      onQuickUpdate = { quickUpdateItem ->
        mainViewModel.updateItem(quickUpdateItem)
      }
    )
    return
  }

  Scaffold(
    topBar = {
      TopAppBar(title = { Text("Todo List") })
    }) { paddingValues ->
    Row(
      modifier = Modifier
        .padding(paddingValues)
        .padding(8.dp)
        .fillMaxSize()
        .horizontalScroll(rememberScrollState())
    ) {
      groups.forEach { groupWithItems: Pair<TodoGroup, List<TodoItem>> ->
        TodoGroupColumn(
          group = groupWithItems.first,
          items = groupWithItems.second,
          modifier = Modifier.fillMaxHeight(),
          onAddItemClick = {groupId ->
            addItemToGroup = groupId
          },
          onItemCheckedChange = { item, isChecked ->
            mainViewModel.updateItemChecked(item, isChecked)
          },
          onDeleteGroupClick = { group -> mainViewModel.deleteGroup(group) },
          onItemDoubleTap = { item ->
            selectedItemIdForDetails = item.id
          }
        )
      }
      Box(
        modifier = Modifier
          .width(250.dp)
          .fillMaxHeight()
          .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
      ) {
        Button(onClick = { showAddGroupDialog = true }) {
          Icon(Icons.Default.Add, contentDescription = "Add New Group")
          Spacer(modifier = Modifier.width(4.dp))
          Text("Add Group")
        }
      }
    }
  }

  if (showAddGroupDialog) {
    AddGroupDialog(onDismiss = {
      showAddGroupDialog = false
    }, onConfirm = {groupName ->
      mainViewModel.addGroup(groupName)
      showAddGroupDialog = false
    })
  }

  addItemToGroup?.let { groupId ->
    AddTodoDialog(
      onDismiss = { addItemToGroup = null },
      onConfirm = { itemTitle, itemDescription ->
        mainViewModel.addItem(groupId, itemTitle, itemDescription)
        addItemToGroup = null
      }
    )
  }
}