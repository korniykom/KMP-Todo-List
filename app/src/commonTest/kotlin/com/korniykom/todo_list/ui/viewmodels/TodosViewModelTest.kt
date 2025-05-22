package com.korniykom.todo_list.ui.viewmodels

import com.korniykom.todo_list.domain.model.Todo
import com.korniykom.todo_list.domain.repository.NetworkRepository
import com.korniykom.todo_list.ui.viewmodels.mvvm.TodosViewModel
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

class TodosViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeTodoRepository: FakeTodoRepository
    private lateinit var fakeNetworkRepository: NetworkRepository
    private lateinit var viewModel: TodosViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeTodoRepository = FakeTodoRepository()
        fakeNetworkRepository = FakeNetworkRepository()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initial_state_has_empty_todo_when_repository_is_empty() = runTest {
        viewModel = TodosViewModel(fakeTodoRepository, fakeNetworkRepository)

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.todos.value.isEmpty())
    }

    @Test
    fun ip_fetched_during_initialization() = runTest {
        val expectedIp = "8.8.8.8"

        viewModel = TodosViewModel(fakeTodoRepository, fakeNetworkRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedIp, viewModel.publicIp.value)
    }

    @Test
    fun todos_state_reflects_repository_changes() = runTest {
        val testTodos = listOf(
            Todo(id = 1, title = "Task 1", description = "Description 1", isCompleted = false),
            Todo(id = 2, title = "Task 2", description = "Description 2", isCompleted = true)
        )

        viewModel = TodosViewModel(fakeTodoRepository, fakeNetworkRepository)
        fakeTodoRepository.addTodos(testTodos)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(testTodos, viewModel.todos.value)
    }

    @Test
    fun onTodoDelete_removes_todo() = runTest {
        val testTodos = listOf(
            Todo(id = 1, title = "Task 1", description = "Description 1", isCompleted = false),
            Todo(id = 2, title = "Task 2", description = "Description 2", isCompleted = true)
        )

        viewModel = TodosViewModel(fakeTodoRepository, fakeNetworkRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        fakeTodoRepository.addTodos(testTodos)
        testDispatcher.scheduler.advanceUntilIdle()

        println("Before delete ${viewModel.todos.value}")

        viewModel.onTodoDelete(2)
        testDispatcher.scheduler.advanceUntilIdle()

        println("After delete ${viewModel.todos.value}")

        val remainingTodos = viewModel.todos.value
        assertEquals(1, remainingTodos.size)
        assertFalse(remainingTodos.any {it.id == 2L})
        assertTrue(remainingTodos.any {it.id == 1L})
    }
}