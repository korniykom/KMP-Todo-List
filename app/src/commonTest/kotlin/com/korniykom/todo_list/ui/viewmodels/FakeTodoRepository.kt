package com.korniykom.todo_list.ui.viewmodels

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTodoRepository: TodoRepository {
    private val todoItems = mutableListOf<Todo>()
    private val todoFlow = MutableStateFlow<List<Todo>>(emptyList())

    override fun getTodos(): Flow<List<Todo>> {
        return todoFlow
    }

    override suspend fun getTodoById(id: Long): Todo? {
        return todoItems.find { it.id == id }
    }

    override suspend fun insertTodo(todo: Todo): Long {
        val newTodo = if (todo.id == 0L) {
            val newId = todoItems.maxOfOrNull { it.id }?.plus(1) ?: 1
            todo.copy(id = newId)
        } else {
            todo
        }
        todoItems.add(newTodo)
        updateFlowValue()
        return newTodo.id
    }

    override suspend fun updateTodo(todo: Todo) {
        val index = todoItems.indexOfFirst { it.id == todo.id }
        if(index != -1) {
            todoItems[index] = todo
            updateFlowValue()
        }
    }

    override suspend fun deleteTodo(id: Long) {
        val initialSize = todoItems.size
        todoItems.removeAll { it.id == id }

        if (todoItems.size != initialSize) {
            updateFlowValue()
        }
    }

    private fun updateFlowValue() {
        todoFlow.value = todoItems.toList()
    }

    fun addTodos(todos: List<Todo>) {
        todoItems.clear()
        todoItems.addAll(todos)
        todoFlow.value = todoItems.toList()
    }

    fun clearTodos() {
        todoItems.clear()
        todoFlow.value = emptyList()
    }
}