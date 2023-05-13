package com.thomaskioko.tvmaniac.seasondetails.api

import com.thomaskioko.tvmaniac.core.db.Seasons
import com.thomaskioko.tvmaniac.core.db.SelectSeasonWithEpisodes
import com.thomaskioko.tvmaniac.core.networkutil.Either
import com.thomaskioko.tvmaniac.core.networkutil.Failure
import kotlinx.coroutines.flow.Flow

interface SeasonDetailsRepository {

    fun observeSeasonsStream(traktId: Long): Flow<Either<Failure, List<Seasons>>>

    fun observeSeasonDetailsStream(traktId: Long): Flow<Either<Failure, List<SelectSeasonWithEpisodes>>>

    fun observeSeasonDetails(traktId: Long): Flow<Either<Failure, List<SelectSeasonWithEpisodes>>>
}