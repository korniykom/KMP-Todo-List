package com.korniykom.todo_list.ui.viewmodels.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.NetworkRepository
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodosViewModel(
    val todoRepository : TodoRepository,
    private val networkRepository : NetworkRepository,
) : ViewModel() {
    private var _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos : StateFlow<List<Todo>> = _todos

    private val _publicIp = MutableStateFlow("No IP address")
    val publicIp : StateFlow<String> = _publicIp

    init {
        viewModelScope.launch(Dispatchers.Default) {
            todoRepository.getTodos().collect { todosList ->
                _todos.value = todosList
            }
        }

        viewModelScope.launch(Dispatchers.Default) {
            try {
                _publicIp.value = networkRepository.getPublicIp()
            } catch (e : Exception) {
                _publicIp.value = "Unable to fetch IP address"
            }
        }
    }

    fun onTodoDelete(id : Long) {
        viewModelScope.launch(Dispatchers.Default) {
            todoRepository.deleteTodo(id)
        }
    }

    fun onToggleChecked(todo : Todo) {
        viewModelScope.launch(Dispatchers.Default) {
            val currentCheckedState = todo.isCompleted
            val newTodo = todo.copy(isCompleted = ! currentCheckedState)
            todoRepository.updateTodo(newTodo)
        }
    }
}