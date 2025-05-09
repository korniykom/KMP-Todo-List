package com.korniykom.todo_list.ui

import androidx.lifecycle.SavedStateHandle
import com.korniykom.todo_list.ui.viewmodels.mvi.EditTodoViewModel
import com.korniykom.todo_list.ui.viewmodels.mvvm.TodosViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::TodosViewModel)
    viewModel { (handle: SavedStateHandle) ->
        EditTodoViewModel(
            todoRepository = get(),
            savedStateHandle = handle
        )
    }}