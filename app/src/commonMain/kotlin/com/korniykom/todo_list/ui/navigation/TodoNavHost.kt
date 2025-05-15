package com.korniykom.todo_list.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.korniykom.todo_list.ui.screens.EditTodoScreen
import com.korniykom.todo_list.ui.screens.LoadingScreen
import com.korniykom.todo_list.ui.screens.TodosScreen
import com.korniykom.todo_list.ui.viewmodels.mvi.EditTodoViewModel
import com.korniykom.todo_list.ui.viewmodels.mvvm.TodosViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TodoNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Loading.route
) {
    val todosViewModel = koinViewModel<TodosViewModel>()

    NavHost(
        navController = navController, startDestination = startDestination
    ) {

        composable(route = Screen.Loading.route) {
            LoadingScreen(onLoadingFinished = { navController.navigate(Screen.Todos.route) })
        }
        composable(route = Screen.Todos.route) {
            TodosScreen(
                viewModel = todosViewModel,
                onEdit = { todoId ->
                    navController.navigate(Screen.Edit.createRoute(todoId))
                }, onSave = { navController.navigate(Screen.Edit.createRoute(0)) })
        }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(
                navArgument("todoId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: 0L
            val editTodoViewModel = koinViewModel<EditTodoViewModel>(
                parameters = { parametersOf(SavedStateHandle(mapOf("todoId" to todoId))) }
            )

            EditTodoScreen(
                viewModel = editTodoViewModel,
                onSave = { navController.popBackStack() }
            )
        }

    }
}