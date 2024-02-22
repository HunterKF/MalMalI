package core.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.Logging

object KtorClient {
    val client = HttpClient() {
        install(Logging)
    }
}