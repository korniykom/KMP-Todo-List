package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

expect class TodoRepositoryImpl : TodoRepository {
    override fun getTodos(): Flow<List<Todo>>
    override suspend fun getTodoById(id: Long): Todo?
    override suspend fun insertTodo(todo: Todo): Long
    override suspend fun updateTodo(todo: Todo)
    override suspend fun deleteTodo(id: Long)
}