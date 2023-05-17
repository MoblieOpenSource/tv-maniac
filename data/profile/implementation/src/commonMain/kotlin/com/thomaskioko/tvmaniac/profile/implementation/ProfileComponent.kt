package com.thomaskioko.tvmaniac.profile.implementation

import com.thomaskioko.tvmaniac.profile.api.ProfileDao
import com.thomaskioko.tvmaniac.profile.api.ProfileRepository
import com.thomaskioko.tvmaniac.profile.api.favorite.FavoriteListDao
import com.thomaskioko.tvmaniac.profile.api.followed.FollowedRepository
import com.thomaskioko.tvmaniac.profile.api.stats.StatsDao
import com.thomaskioko.tvmaniac.profile.api.stats.StatsRepository
import com.thomaskioko.tvmaniac.profile.implementation.favorite.FavoriteListDaoImpl
import com.thomaskioko.tvmaniac.profile.implementation.followed.FollowedRepositoryImpl
import com.thomaskioko.tvmaniac.profile.implementation.stats.StatsDaoImpl
import com.thomaskioko.tvmaniac.profile.implementation.stats.StatsRepositoryImpl
import me.tatarka.inject.annotations.Provides

interface ProfileComponent {

    @Provides
    fun provideProfileDao(bind: ProfileDaoImpl): ProfileDao = bind

    @Provides
    fun provideFavoriteListDao(bind: FavoriteListDaoImpl): FavoriteListDao = bind

    @Provides
    fun provideStatsDao(bind: StatsDaoImpl): StatsDao = bind

    @Provides
    fun provideProfileRepository(bind: ProfileRepositoryImpl): ProfileRepository = bind

    @Provides
    fun provideFollowedRepository(bind: FollowedRepositoryImpl): FollowedRepository = bind

    @Provides
    fun provideStatsRepository(bind: StatsRepositoryImpl): StatsRepository = bind
}
