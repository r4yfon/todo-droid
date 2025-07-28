package com.safarayfon.androidtodo.ui.main

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.safarayfon.androidtodo.data.database.TodoDatabase
import com.safarayfon.androidtodo.data.entities.TodoGroup
import com.safarayfon.androidtodo.data.entities.TodoItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val todoDao = TodoDatabase.getDatabase(application).todoDao()
  private val _groupsWithItems = mutableStateOf<List<Pair<TodoGroup, List<TodoItem>>>>(emptyList())

  val groupsWithItems: State<List<Pair<TodoGroup, List<TodoItem>>>> = _groupsWithItems

  init {
    loadAllGroupsWithItems()
  }

  private fun loadAllGroupsWithItems() {
    viewModelScope.launch {
        val groups = todoDao.getAllGroups().first()
        val groupsWithItems = mutableListOf<Pair<TodoGroup, List<TodoItem>>>()

        for (group in groups) {
          val items = todoDao.getItemsByGroup(group.id).first()
          groupsWithItems.add(Pair(group, items))
        }

        _groupsWithItems.value = groupsWithItems
    }
  }

  fun addGroup(groupName: String) {
    viewModelScope.launch {
      if (groupName.isNotBlank()) {
        todoDao.insertGroup(
          TodoGroup(name = groupName)
        )
        loadAllGroupsWithItems()
      }
    }
  }

  fun deleteGroup(group: TodoGroup) {
    viewModelScope.launch {
      val items = todoDao.getItemsByGroup(group.id).first()
      items.forEach { item ->
        todoDao.deleteItem(item)
      }

      todoDao.deleteGroup(group)
      loadAllGroupsWithItems()
    }
  }

  fun addItem(groupId: Long, title: String, description: String = "") {
    viewModelScope.launch {
      if (title.isNotBlank()) {
        todoDao.insertItem(
          TodoItem(
            title = title, groupId = groupId, details = description, isCompleted = false
          )
        )
        loadAllGroupsWithItems()
      }
    }
  }

  fun updateItemChecked(item: TodoItem, isChecked: Boolean) {
    viewModelScope.launch {
      todoDao.updateItem(item.copy(isCompleted = isChecked))
      loadAllGroupsWithItems()
    }
  }


  fun deleteItem(item: TodoItem) {
    viewModelScope.launch {
      todoDao.deleteItem(item)
      loadAllGroupsWithItems()
    }
  }

  fun updateItem(item: TodoItem, onFinished: () -> Unit = {}) {
    viewModelScope.launch {
      todoDao.updateItem(item)
      loadAllGroupsWithItems()
      onFinished()
    }
  }
  fun getItemById(id: Long): TodoItem? {
    return groupsWithItems.value.flatMap { it.second }.find { it.id == id }
  }
  fun getItemFlowById(id: Long): kotlinx.coroutines.flow.Flow<TodoItem?> {
    return todoDao.getItemByIdFlow(id)
  }



}