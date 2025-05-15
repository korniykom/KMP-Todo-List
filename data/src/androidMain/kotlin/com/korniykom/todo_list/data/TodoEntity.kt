package com.korniykom.todo_list.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.korniykom.todo_list.domain.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean
) {
    fun toDomainModel(): Todo {
        return Todo(
            id = id, title = title, description = description, isCompleted = isCompleted
        )
    }

    companion object {
        fun fromDomainModel(todo: Todo): TodoEntity {
            return TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isCompleted = todo.isCompleted
            )
        }
    }
}