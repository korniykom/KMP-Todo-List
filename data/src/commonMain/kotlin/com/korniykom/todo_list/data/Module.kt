package com.korniykom.todo_list.data

import org.koin.dsl.module

val dataModule = module {
    single {
        TodoRepository()
    }
}