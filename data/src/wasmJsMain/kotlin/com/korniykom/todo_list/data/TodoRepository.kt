package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

actual class TodoRepository {
    private val dummyTodo = mutableListOf(
        Todo(
            id = 1, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 1, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 1, title = "todo1", description = "todo", isCompleted = false
        ), Todo(
            id = 1, title = "todo1", description = "todo", isCompleted = false
        )
    )
    private val _todosFlow = MutableStateFlow(dummyTodo.toList())

    actual fun getTodos(): Flow<List<Todo>> = _todosFlow

    actual suspend fun getTodoById(id: Long): Todo? {
        return dummyTodo.find { it.id == id }
    }

    actual suspend fun insertTodo(todo: Todo): Long {
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

    actual suspend fun updateTodo(todo: Todo) {
        val index = dummyTodo.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            dummyTodo[index] = todo
            _todosFlow.update { dummyTodo.toList() }
        }
    }

    actual suspend fun deleteTodo(id: Long) {

    }
}