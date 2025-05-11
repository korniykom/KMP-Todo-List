package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow


actual class TodoRepositoryImpl(
    private val todoDao: TodoDao
): TodoRepository {
    actual override fun getTodos() : Flow<List<Todo>> {
        return flow {
            val todoList = todoDao.getTodos().map { it.toDomainModel() }
            emit(todoList)
        }
    }

    actual override suspend fun getTodoById(id : Long) : Todo? {
        return todoDao.getTodoById(id)?.toDomainModel()
    }

    actual override suspend fun insertTodo(todo : Todo) : Long {
        return todoDao.insertTodo(TodoEntity.fromDomainModel(todo))
    }

    actual override suspend fun updateTodo(todo : Todo) {
        todoDao.updateTodo(TodoEntity.fromDomainModel(todo))
    }

    actual override suspend fun deleteTodo(id : Long) {
        todoDao.deleteTodo(id)
    }
}