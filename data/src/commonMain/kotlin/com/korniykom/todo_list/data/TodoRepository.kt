package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import kotlinx.coroutines.flow.Flow

expect class TodoRepository() {
    fun getTodos() : Flow<List<Todo>>
    suspend fun getTodoById(id : Long) : Todo?
    suspend fun insertTodo(todo : Todo) : Long
    suspend fun updateTodo(todo : Todo)
    suspend fun deleteTodo(id : Long)
}