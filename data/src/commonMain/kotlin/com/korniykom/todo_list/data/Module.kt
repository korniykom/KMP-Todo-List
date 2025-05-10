package com.korniykom.todo_list.data

import com.korniykom.todo_list.data.NetworkRepositoryImpl
import com.korniykom.todo_list.domain.repository.NetworkRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    includes(platformModule())
    singleOf(::TodoRepository)
//    singleOf(::NetworkRepositoryImpl).bind<NetworkRepository>()
}

expect fun platformModule() : Module