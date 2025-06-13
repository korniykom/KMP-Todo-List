package com.korniykom.todo_list.domain.repository

interface NetworkRepository {
    suspend fun getPublicIp(): String
}