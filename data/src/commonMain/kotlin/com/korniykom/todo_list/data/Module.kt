package com.korniykom.todo_list.data

import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module {
    includes(platformModule())
}

expect fun platformModule() : Module