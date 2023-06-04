package com.thomaskioko.tvmaniac.traktauth.implementation

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import com.thomaskioko.tvmaniac.traktauth.api.TraktAuthManager
import com.thomaskioko.tvmaniac.util.model.Configs
import com.thomaskioko.tvmaniac.util.scope.ActivityScope
import com.thomaskioko.tvmaniac.util.scope.ApplicationScope
import me.tatarka.inject.annotations.Provides
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues

interface TraktAuthComponent {

    @ApplicationScope
    @Provides
    fun provideAuthConfig(): AuthorizationServiceConfiguration {
        return AuthorizationServiceConfiguration(
            Uri.parse("https://trakt.tv/oauth/authorize"),
            Uri.parse("https://trakt.tv/oauth/token"),
        )
    }

    @Provides
    fun provideAuthRequest(
        configuration: AuthorizationServiceConfiguration,
        configs: Configs,
    ): AuthorizationRequest = AuthorizationRequest.Builder(
        configuration,
        configs.traktClientId,
        ResponseTypeValues.CODE,
        configs.traktRedirectUri.toUri(),
    ).apply {
        setCodeVerifier(null)
    }.build()

    @ApplicationScope
    @Provides
    fun provideClientAuth(
        configs: Configs,
    ): ClientAuthentication = ClientSecretBasic(configs.traktClientSecret)

    @ApplicationScope
    @Provides
    fun provideAuthorizationService(
        application: Application,
    ): AuthorizationService = AuthorizationService(application)
}

interface TraktAuthManagerComponent {

    @ActivityScope
    @Provides
    fun provideTraktAuthManager(bind: TraktAuthManagerImpl): TraktAuthManager = bind
}
