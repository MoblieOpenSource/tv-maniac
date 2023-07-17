package com.thomaskioko.tvmaniac.tmdb.implementation

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val TIMEOUT_DURATION: Long = 60_000

fun tmdbHttpClient(
    tmdbApiKey: String,
    json: Json,
    httpClientEngine: HttpClientEngine,
) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json = json)
    }

    install(DefaultRequest) {
        apply {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"

                parameters.append("api_key", tmdbApiKey)

                headers {
                    append(HttpHeaders.Accept, "application/vnd.api+json")
                    append(HttpHeaders.ContentType, "application/vnd.api+json")
                }
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = TIMEOUT_DURATION
        connectTimeoutMillis = TIMEOUT_DURATION
        socketTimeoutMillis = TIMEOUT_DURATION
    }
}
