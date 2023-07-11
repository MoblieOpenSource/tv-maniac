package com.thomaskioko.trakt.service.implementation

import com.thomaskioko.trakt.service.implementation.inject.TraktHttpClient
import com.thomaskioko.tvmaniac.core.networkutil.ApiResponse
import com.thomaskioko.tvmaniac.core.networkutil.NetworkExceptionHandler
import com.thomaskioko.tvmaniac.core.networkutil.safeRequest
import com.thomaskioko.tvmaniac.trakt.api.TraktListRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.model.ErrorResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktAddRemoveShowFromListResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktAddShowRequest
import com.thomaskioko.tvmaniac.trakt.api.model.TraktAddShowToListResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktCreateListRequest
import com.thomaskioko.tvmaniac.trakt.api.model.TraktCreateListResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktFollowedShowResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktPersonalListsResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktShow
import com.thomaskioko.tvmaniac.trakt.api.model.TraktShowIds
import com.thomaskioko.tvmaniac.trakt.api.model.TraktUserResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import me.tatarka.inject.annotations.Inject

@Inject
class TraktListRemoteDataSourceImpl(
    private val httpClient: TraktHttpClient,
    private val exceptionHandler: NetworkExceptionHandler,
) : TraktListRemoteDataSource {

    override suspend fun getUser(userId: String): ApiResponse<TraktUserResponse, ErrorResponse> =
        httpClient.safeRequest(exceptionHandler) {
            url {
                method = HttpMethod.Get
                path("users/$userId")
                parameter("extended", "full")
            }
        }

    override suspend fun getUserList(userId: String): List<TraktPersonalListsResponse> =
        httpClient.get("users/$userId/lists").body()

    override suspend fun createFollowingList(userSlug: String): TraktCreateListResponse =
        httpClient.post("users/$userSlug/lists") {
            setBody(TraktCreateListRequest())
        }.body()

    override suspend fun getFollowedList(
        listId: Long,
        userSlug: String,
    ): List<TraktFollowedShowResponse> =
        httpClient.get("users/$userSlug/lists/$listId/items/shows") {
            parameter("sort_by", "added")
        }.body()

    override suspend fun getWatchList(): List<TraktFollowedShowResponse> =
        httpClient.get("sync/watchlist/shows") {
            parameter("sort_by", "added")
        }.body()

    override suspend fun addShowToWatchList(showId: Long): TraktAddShowToListResponse =
        httpClient.post("sync/watchlist") {
            setBody(
                TraktAddShowRequest(
                    shows = listOf(
                        TraktShow(
                            ids = TraktShowIds(
                                traktId = showId.toInt(),
                            ),
                        ),
                    ),
                ),
            )
        }.body()

    override suspend fun removeShowFromWatchList(showId: Long): TraktAddRemoveShowFromListResponse =
        httpClient.post("sync/watchlist/remove") {
            contentType(ContentType.Application.Json)
            setBody(
                TraktAddShowRequest(
                    shows = listOf(
                        TraktShow(
                            ids = TraktShowIds(
                                traktId = showId.toInt(),
                            ),
                        ),
                    ),
                ),
            )
        }.body()

    override suspend fun addShowToList(
        userSlug: String,
        listId: Long,
        traktShowId: Long,
    ): TraktAddShowToListResponse =
        httpClient.post("users/$userSlug/lists/$listId/items") {
            setBody(
                TraktAddShowRequest(
                    shows = listOf(
                        TraktShow(
                            ids = TraktShowIds(
                                traktId = traktShowId.toInt(),
                            ),
                        ),
                    ),
                ),
            )
        }.body()
}
