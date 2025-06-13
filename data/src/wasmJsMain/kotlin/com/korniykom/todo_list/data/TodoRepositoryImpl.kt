package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

actual class TodoRepositoryImpl : TodoRepository {
    private val dummyTodo = mutableListOf(
        Todo(
            id = 1, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 2, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 3, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 4, title = "todo1", description = "todo", isCompleted = false
        )
    )
    private val _todosFlow = MutableStateFlow(dummyTodo.toList())

    actual override fun getTodos(): Flow<List<Todo>> = _todosFlow

    actual override suspend fun getTodoById(id: Long): Todo? {
        return dummyTodo.find { it.id == id }
    }

    actual override suspend fun insertTodo(todo: Todo): Long {
        val newTodo = if (todo.id <= 0) {
            val newId = (dummyTodo.maxOfOrNull { it.id } ?: 0) + 1
            todo.copy(id = newId)
        } else {
            todo
        }

        dummyTodo.add(newTodo)
        _todosFlow.update { dummyTodo.toList() }
        return newTodo.id
    }

    actual override suspend fun updateTodo(todo: Todo) {
        val index = dummyTodo.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            dummyTodo[index] = todo
            _todosFlow.update { dummyTodo.toList() }
        }
    }

    actual override suspend fun deleteTodo(id: Long) {
        val index = dummyTodo.indexOfFirst { it.id == id }
        if (index != -1) {
            dummyTodo.removeAt(index)
            _todosFlow.update { dummyTodo.toList() }
        }
    }
}