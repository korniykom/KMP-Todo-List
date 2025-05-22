package com.korniykom.todo_list.ui.viewmodels

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.swipeLeft
import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.ui.screens.TodosScreen
import com.korniykom.todo_list.ui.viewmodels.mvvm.TodosViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.BeforeTest
import kotlin.test.Test


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTestApi::class)
class TodosScreenTestWithRealViewModel {

    private lateinit var todoRepository: FakeTodoRepository
    private lateinit var networkRepository: FakeNetworkRepository
    private lateinit var viewModel: TodosViewModel

    private val testTodos = listOf(
        Todo(id = 1L, title = "Todo1", description = "Description1", isCompleted = false),
        Todo(id = 2L, title = "Todo2", description = "Description2", isCompleted = true)
    )

    @BeforeTest
    fun setup() {
        todoRepository = FakeTodoRepository()
        networkRepository = FakeNetworkRepository()
        todoRepository.addTodos(testTodos)
        viewModel = TodosViewModel(todoRepository, networkRepository)
    }

    @Test
    fun todosScreen_displays_items() = runComposeUiTest {
        setContent {
            TodosScreen(
                viewModel = viewModel,
                onEdit = { },
                onSave = { }
            )
        }

        onNodeWithText("Todo1").assertIsDisplayed()
        onNodeWithText("Todo2").assertIsDisplayed()
        onNodeWithText("IP: 8.8.8.8").assertIsDisplayed()
    }

    @Test
    fun todosScreen_deletes_item_when_swiped_correct() = runComposeUiTest {
        setContent {
            TodosScreen(
                viewModel = viewModel,
                onEdit = { },
                onSave = { }
            )
        }

        onNodeWithText("Todo1").performTouchInput { swipeLeft() }
        onNodeWithText("Todo1").assertDoesNotExist()
    }
}
