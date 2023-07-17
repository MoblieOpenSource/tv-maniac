package com.thomaskioko.trakt.service.implementation.inject

import com.thomaskioko.trakt.service.implementation.TraktListRemoteDataSourceImpl
import com.thomaskioko.trakt.service.implementation.TraktShowsShowsRemoteDataSourceImpl
import com.thomaskioko.trakt.service.implementation.TraktStatsRemoteDataSourceImpl
import com.thomaskioko.trakt.service.implementation.TraktTokenRemoteDataSourceImpl
import com.thomaskioko.trakt.service.implementation.TraktUserRemoteDataSourceImpl
import com.thomaskioko.trakt.service.implementation.traktHttpClient
import com.thomaskioko.tvmaniac.trakt.api.TraktListRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.TraktShowsRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.TraktStatsRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.TraktTokenRemoteDataSource
import com.thomaskioko.tvmaniac.trakt.api.TraktUserRemoteDataSource
import com.thomaskioko.tvmaniac.util.KermitLogger
import com.thomaskioko.tvmaniac.util.model.Configs
import com.thomaskioko.tvmaniac.util.scope.ApplicationScope
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

typealias TraktHttpClient = HttpClient
typealias TraktJson = Json

interface TraktComponent {

    @ApplicationScope
    @Provides
    fun provideJson(): TraktJson = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    @ApplicationScope
    @Provides
    fun provideHttpClient(
        configs: Configs,
        json: TraktJson,
        httpClientEngine: TraktHttpClientEngine,
        logger: KermitLogger,
    ): TraktHttpClient = traktHttpClient(
        isDebug = configs.isDebug,
        traktClientId = configs.traktClientId,
        json = json,
        httpClientEngine = httpClientEngine,
        kermitLogger = logger,
    )

    @ApplicationScope
    @Provides
    fun provideTraktListRemoteDataSource(
        bind: TraktListRemoteDataSourceImpl,
    ): TraktListRemoteDataSource = bind

    @ApplicationScope
    @Provides
    fun provideTraktShowsRemoteDataSource(
        bind: TraktShowsShowsRemoteDataSourceImpl,
    ): TraktShowsRemoteDataSource = bind

    @ApplicationScope
    @Provides
    fun provideTraktStatsRemoteDataSource(
        bind: TraktStatsRemoteDataSourceImpl,
    ): TraktStatsRemoteDataSource = bind

    @ApplicationScope
    @Provides
    fun provideTraktTokenRemoteDataSource(
        bind: TraktTokenRemoteDataSourceImpl,
    ): TraktTokenRemoteDataSource = bind

    @ApplicationScope
    @Provides
    fun provideTraktUserRemoteDataSource(
        bind: TraktUserRemoteDataSourceImpl,
    ): TraktUserRemoteDataSource = bind
}
