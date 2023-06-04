package com.thomaskioko.trakt.service.implementation

import com.thomaskioko.tvmaniac.datastore.api.DatastoreRepository
import com.thomaskioko.tvmaniac.util.model.AppCoroutineDispatchers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import me.tatarka.inject.annotations.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
class TraktAuthInterceptor(
    private val dispatchers: AppCoroutineDispatchers,
    private val datastoreRepository: DatastoreRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val authState = runBlocking(dispatchers.io) {
            datastoreRepository.getAuthState()
        }

        request = request.newBuilder()
            .addHeader(HttpHeaders.ContentType, "application/json")
            .addHeader(HttpHeaders.Authorization, "Bearer ${authState.accessToken}")
            .build()
        return chain.proceed(request)
    }
}
