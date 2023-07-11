package com.thomaskioko.trakt.service.implementation

import com.thomaskioko.trakt.service.implementation.inject.TraktHttpClient
import com.thomaskioko.tvmaniac.trakt.api.TraktTokenRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.model.AccessTokenBody
import com.thomaskioko.tvmaniac.trakt.api.model.RefreshAccessTokenBody
import com.thomaskioko.tvmaniac.trakt.api.model.TraktAccessRefreshTokenResponse
import com.thomaskioko.tvmaniac.trakt.api.model.TraktAccessTokenResponse
import com.thomaskioko.tvmaniac.util.model.Configs
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import me.tatarka.inject.annotations.Inject

@Inject
class TraktTokenRemoteDataSourceImpl(
    private val configs: Configs,
    private val httpClient: TraktHttpClient,
) : TraktTokenRemoteDataSource {

    override suspend fun getAccessToken(
        authCode: String,
    ): TraktAccessTokenResponse = httpClient.post("oauth/token") {
        setBody(
            AccessTokenBody(
                code = authCode,
                clientId = configs.traktClientId,
                clientSecret = configs.traktClientSecret,
                redirectUri = configs.traktRedirectUri,
                grantType = "authorization_code",
            ),
        )
    }.body()

    override suspend fun getAccessRefreshToken(
        refreshToken: String,
    ): TraktAccessRefreshTokenResponse = httpClient.post("oauth/token") {
        setBody(
            RefreshAccessTokenBody(
                refreshToken = refreshToken,
            ),
        )
    }.body()

    override suspend fun revokeAccessToken(
        authCode: String,
    ) {
        httpClient.post("oauth/revoke") {
            setBody(
                AccessTokenBody(
                    code = authCode,
                    clientId = configs.traktClientId,
                    clientSecret = configs.traktClientSecret,
                    redirectUri = configs.traktRedirectUri,
                ),
            )
        }
    }
}
