package core.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

object KtorClient {
    /*val client = HttpClient(Android) {
        install(Loggi)
        install(Auth) {
            bearer {
                BearerTokens("", "")
            }
        }
    }*/
}