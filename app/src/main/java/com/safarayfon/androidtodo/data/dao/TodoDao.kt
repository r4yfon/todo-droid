package com.safarayfon.androidtodo.data.dao

import androidx.room.*
import com.safarayfon.androidtodo.data.entities.TodoGroup
import com.safarayfon.androidtodo.data.entities.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface   TodoDao {
    @Query("SELECT * FROM todo_groups ORDER BY position")
    fun getAllGroups(): Flow<List<TodoGroup>>

    @Query("SELECT * FROM todo_items WHERE groupId = :groupId ORDER BY creationDate")
    fun getItemsByGroup(groupId: Long): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Long): TodoItem?

    @Insert
    suspend fun insertGroup(group: TodoGroup): Long

    @Insert
    suspend fun insertItem(item: TodoItem): Long

    @Update
    suspend fun updateItem(item: TodoItem)

    @Delete
    suspend fun deleteItem(item: TodoItem)

    @Delete
    suspend fun deleteGroup(group: TodoGroup)

    @Query("SELECT * FROM todo_items WHERE id = :itemId")
    fun getItemByIdFlow(itemId: Long): kotlinx.coroutines.flow.Flow<TodoItem?>

}
