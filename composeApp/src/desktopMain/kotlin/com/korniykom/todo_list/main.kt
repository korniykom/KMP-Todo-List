package com.korniykom.todo_list

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Todo List",
    ) {
        App()
    }
}