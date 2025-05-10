package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.repository.NetworkRepository

actual class NetworkRepositoryImpl: NetworkRepository {
    actual override suspend fun getPublicIp(): String {
        return "Web Network Repository Implementation"
    }
}