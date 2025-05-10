package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.repository.NetworkRepository

expect class NetworkRepositoryImpl: NetworkRepository {
    override suspend fun getPublicIp(): String
}