package com.korniykom.todo_list.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.korniykom.todo_list.domain.repository.NetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class NetworkRepositoryImpl: NetworkRepository {
    private val client = createHttpClient()

    @RequiresApi(Build.VERSION_CODES.O)
    actual override suspend fun getPublicIp(): String {
        return client.get("https://api.ipify.org").bodyAsText()
    }
}

internal fun createHttpClient(): HttpClient {
    return HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}
