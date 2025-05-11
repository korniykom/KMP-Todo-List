package com.korniykom.todo_list.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.korniykom.todo_list.domain.repository.NetworkRepository
import com.korniykom.todo_list.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule() = module {
    singleOf(::NetworkRepositoryImpl).bind<NetworkRepository>()
    singleOf(::TodoRepositoryImpl).bind<TodoRepository>()
    single {
        Room.databaseBuilder<TodoDatabase>(
            name = "todo_database",
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }.bind<TodoDatabase>()
    single { get<TodoDatabase>().todoDao() }
}

