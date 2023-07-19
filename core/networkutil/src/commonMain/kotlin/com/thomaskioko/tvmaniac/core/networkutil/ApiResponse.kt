package com.thomaskioko.tvmaniac.core.networkutil

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        ApiResponse.Success(response.body())
    } catch (exception: ClientRequestException) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = "Status Code: ${exception.response.status.value} - API Key Missing",
        )
    } catch (exception: HttpExceptions) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = exception.message,
        )
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(
            message = e.message,
            errorMessage = "Something went wrong",
        )
    } catch (e: Exception) {
        ApiResponse.Error.GenericError(
            message = e.message,
            errorMessage = "Something went wrong",
        )
    }

sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server errors.
         * @param code HTTP Status code
         * @param errorBody Response body
         * @param errorMessage Custom error message
         */
        data class HttpError<E>(
            val code: Int,
            val errorBody: String?,
            val errorMessage: String?,
        ) : Error<E>()

        /**
         * Represent SerializationExceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class SerializationError(
            val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>()

        /**
         * Represent other exceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class GenericError(
            val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>()
    }
}

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String,
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}
