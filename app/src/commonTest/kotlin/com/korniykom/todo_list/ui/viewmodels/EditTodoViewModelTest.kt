package com.korniykom.todo_list.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.ui.viewmodels.mvi.EditTodoViewModel
import com.korniykom.todo_list.ui.viewmodels.mvi.EditTodoIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EditTodoViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var viewModel: EditTodoViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTodoRepository = FakeTodoRepository()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initial_state_for_new_todo_has_correct_defaults() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        val initialState = viewModel.state.value
        assertEquals(0L, initialState.id)
        assertEquals("", initialState.title)
        assertEquals("", initialState.description)
        assertFalse(initialState.isChecked)
        assertFalse(initialState.isInitialized)
    }

    @Test
    fun initialize_intent_called_twice_not_reinitialize() = runTest {
        val existingTodo =
            Todo(id = 1L, title = "Original", description = "Original", isCompleted = false)
        fakeTodoRepository.addTodos(listOf(existingTodo))

        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 1L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.Initialize)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.processIntent(EditTodoIntent.SetTitle("Modified Title"))

        viewModel.processIntent(EditTodoIntent.Initialize)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals("Modified Title", state.title)
    }

    @Test
    fun set_title_intent_updates_title() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.SetTitle("New Title"))

        assertEquals("New Title", viewModel.state.value.title)
    }

    @Test
    fun set_description_intent_updates_description() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.SetDescription("New Description"))

        assertEquals("New Description", viewModel.state.value.description)
    }

    @Test
    fun set_checked_intent_updates_checked_status() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.SetChecked(true))

        assertTrue(viewModel.state.value.isChecked)

        viewModel.processIntent(EditTodoIntent.SetChecked(false))

        assertFalse(viewModel.state.value.isChecked)
    }

    @Test
    fun multiple_field_updates_work_correctly() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.SetTitle("Test Title"))
        viewModel.processIntent(EditTodoIntent.SetDescription("Test Description"))
        viewModel.processIntent(EditTodoIntent.SetChecked(true))

        val state = viewModel.state.value
        assertEquals("Test Title", state.title)
        assertEquals("Test Description", state.description)
        assertTrue(state.isChecked)
    }

    @Test
    fun state_updates_are_synchronous_for_field_changes() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("todoId" to 0L))
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        viewModel.processIntent(EditTodoIntent.SetTitle("Immediate"))
        assertEquals("Immediate", viewModel.state.value.title)

        viewModel.processIntent(EditTodoIntent.SetDescription("Also Immediate"))
        assertEquals("Also Immediate", viewModel.state.value.description)

        viewModel.processIntent(EditTodoIntent.SetChecked(true))
        assertTrue(viewModel.state.value.isChecked)
    }

    @Test
    fun savedStateHandle_without_todoId_defaults_to_zero() = runTest {
        val savedStateHandle = SavedStateHandle()
        viewModel = EditTodoViewModel(fakeTodoRepository, savedStateHandle)

        assertEquals(0L, viewModel.state.value.id)
    }
}