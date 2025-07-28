package com.safarayfon.androidtodo.repository

import com.safarayfon.androidtodo.data.dao.TodoDao
import com.safarayfon.androidtodo.data.entities.TodoGroup
import com.safarayfon.androidtodo.data.entities.TodoItem
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllGroups(): Flow<List<TodoGroup>> = todoDao.getAllGroups()

    fun getItemsByGroup(groupId: Long): Flow<List<TodoItem>> = todoDao.getItemsByGroup(groupId)

    suspend fun getItemById(itemId: Long): TodoItem? = todoDao.getItemById(itemId)

    suspend fun insertGroup(group: TodoGroup): Long = todoDao.insertGroup(group)

    suspend fun insertItem(item: TodoItem): Long = todoDao.insertItem(item)

    suspend fun updateItem(item: TodoItem) = todoDao.updateItem(item)

    suspend fun deleteItem(item: TodoItem) = todoDao.deleteItem(item)

    suspend fun deleteGroup(group: TodoGroup) = todoDao.deleteGroup(group)
}
