package com.korniykom.todo_list.ui

import com.korniykom.todo_list.ui.viewmodels.mvvm.TodosViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::TodosViewModel)
}