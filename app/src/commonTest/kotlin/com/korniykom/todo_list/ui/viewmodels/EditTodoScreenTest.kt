package com.korniykom.todo_list.ui.viewmodels

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.SavedStateHandle
import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.ui.screens.EditTodoScreen
import com.korniykom.todo_list.ui.viewmodels.mvi.EditTodoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTestApi::class)
class EditTodoScreenTest {
    private lateinit var todoRepository: FakeTodoRepository
    private lateinit var viewModel: EditTodoViewModel

    private val testTodos = listOf(
        Todo(id = 1L, title = "Todo1", description = "Description1", isCompleted = false),
        Todo(id = 2L, title = "Todo2", description = "Description2", isCompleted = true)
    )

    @BeforeTest
    fun setup() {
        todoRepository = FakeTodoRepository()
        todoRepository.addTodos(testTodos)
        viewModel = EditTodoViewModel(todoRepository, SavedStateHandle(mapOf("todoId" to 1L)))
    }

    @Test
    fun editTodoScreen_initialized_correct() = runComposeUiTest {
        setContent {
            EditTodoScreen(
                viewModel = viewModel, onSave = {})
        }

        onNodeWithText("Title").assertIsDisplayed()
        onNodeWithText("Description").assertIsDisplayed()
        onNodeWithText("Save todo").assertIsDisplayed()
    }

    @Test
    fun editTodoScreen_toggle_works() = runComposeUiTest {
        setContent {
            EditTodoScreen(
                viewModel = viewModel, onSave = {})
        }
        val checkboxNode = onNodeWithContentDescription("Checkbox")
        checkboxNode.performClick()
        checkboxNode.assertIsOn()
        checkboxNode.performClick()
        checkboxNode.assertIsOff()
    }
}