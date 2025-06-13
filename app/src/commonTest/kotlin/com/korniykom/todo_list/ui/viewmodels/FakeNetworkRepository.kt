package com.korniykom.todo_list.ui.viewmodels

import com.korniykom.todo_list.domain.repository.NetworkRepository

class FakeNetworkRepository : NetworkRepository {
    val fakeIp = "8.8.8.8"

    override suspend fun getPublicIp(): String {
        return fakeIp
    }

}