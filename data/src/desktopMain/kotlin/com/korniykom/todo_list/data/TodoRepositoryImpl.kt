package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


actual class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {

    private val dbChangeSignal = MutableSharedFlow<Unit>(replay = 1)
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        scope.launch {
            dbChangeSignal.emit(Unit)
        }
    }

    actual override fun getTodos(): Flow<List<Todo>> {
        return dbChangeSignal.flatMapLatest {
            flow {
                val todoList = todoDao.getTodos().map { it.toDomainModel() }
                emit(todoList)
            }
        }.distinctUntilChanged()
    }

    actual override suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)?.toDomainModel()
    }

    actual override suspend fun insertTodo(todo: Todo): Long {
        val id = todoDao.insertTodo(TodoEntity.fromDomainModel(todo))
        notifyDbChanged()
        return id
    }

    actual override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(TodoEntity.fromDomainModel(todo))
        notifyDbChanged()
    }

    actual override suspend fun deleteTodo(id: Long) {
        todoDao.deleteTodo(id)
        notifyDbChanged()
    }

    private fun notifyDbChanged() {
        scope.launch {
            dbChangeSignal.emit(Unit)
        }
    }
}