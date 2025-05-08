package com.korniykom.todo_list.ui.viewmodels.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class EditTodoViewModel (
) : ViewModel() {
val todoId = 5L

    private val _state = MutableStateFlow(EditTodoState(id = todoId))
    val state : StateFlow<EditTodoState> = _state.asStateFlow()

    fun processIntent(intent : EditTodoIntent) {

    }


}

