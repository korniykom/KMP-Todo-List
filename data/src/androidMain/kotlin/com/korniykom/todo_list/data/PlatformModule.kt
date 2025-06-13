package com.korniykom.todo_list.data

import android.content.Context
import androidx.room.Room
import com.korniykom.todo_list.domain.repository.NetworkRepository
import com.korniykom.todo_list.domain.repository.TodoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule() = module {
    singleOf(::NetworkRepositoryImpl).bind<NetworkRepository>()
    singleOf(::TodoRepositoryImpl).bind<TodoRepository>()
    single {
        provideDatabase(get())
    }.bind<TodoDatabase>()
    single { get<TodoDatabase>().todoDao() }
}

fun provideDatabase(context: Context): TodoDatabase {
    val appContext = context.applicationContext
    return Room.databaseBuilder(
        appContext,
        TodoDatabase::class.java,
        "todo_database.db"
    ).build()
}