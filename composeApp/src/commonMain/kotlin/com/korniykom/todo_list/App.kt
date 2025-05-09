package com.korniykom.todo_list

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.korniykom.todo_list.ui.navigation.TodoNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
@Preview
fun App(
    platformModule: Module = Module(),
) {
    KoinApplication(
        application = {
            modules(platformModule)
        }
    ) {
        MaterialTheme {
            TodoNavHost()
        }
    }
}