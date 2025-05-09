package com.korniykom.todo_list.data

import com.korniykom.todo_list.domain.repository.NetworkRepository

class NetworkRepositoryImpl: NetworkRepository {
    override suspend fun getPublicIp(): String {
        return "Network Repository is under construction"
    }
}