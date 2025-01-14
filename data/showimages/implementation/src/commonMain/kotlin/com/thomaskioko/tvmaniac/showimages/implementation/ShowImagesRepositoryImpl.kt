package com.thomaskioko.tvmaniac.showimages.implementation

import com.thomaskioko.tvmaniac.core.db.Show_image
import com.thomaskioko.tvmaniac.core.networkutil.ApiResponse
import com.thomaskioko.tvmaniac.core.networkutil.DefaultError
import com.thomaskioko.tvmaniac.core.networkutil.Either
import com.thomaskioko.tvmaniac.core.networkutil.Failure
import com.thomaskioko.tvmaniac.core.networkutil.NetworkExceptionHandler
import com.thomaskioko.tvmaniac.resourcemanager.api.LastRequest
import com.thomaskioko.tvmaniac.resourcemanager.api.RequestManagerRepository
import com.thomaskioko.tvmaniac.showimages.api.ShowImagesDao
import com.thomaskioko.tvmaniac.showimages.api.ShowImagesRepository
import com.thomaskioko.tvmaniac.tmdb.api.TmdbNetworkDataSource
import com.thomaskioko.tvmaniac.util.FormatterUtil
import com.thomaskioko.tvmaniac.util.KermitLogger
import com.thomaskioko.tvmaniac.util.model.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import kotlin.time.Duration.Companion.days

@Inject
class ShowImagesRepositoryImpl(
    private val networkDataSource: TmdbNetworkDataSource,
    private val imageCache: ShowImagesDao,
    private val formatterUtil: FormatterUtil,
    private val exceptionHandler: NetworkExceptionHandler,
    private val dispatchers: AppCoroutineDispatchers,
    private val logger: KermitLogger,
    private val requestManagerRepository: RequestManagerRepository,
) : ShowImagesRepository {

    override fun updateShowArtWork(): Flow<Either<Failure, Unit>> =
        imageCache.observeShowImages()
            .map { shows ->
                shows.forEach { show ->
                    val shouldFetch = requestManagerRepository.isRequestExpired(
                        entityId = show.trakt_id,
                        requestType = "SHOW_ARTWORK",
                        threshold = 1.days,
                    )
                    if (shouldFetch) {
                        show.tmdb_id?.let { tmdbId ->

                            when (val response = networkDataSource.getTvShowDetails(tmdbId)) {
                                is ApiResponse.Error -> {
                                    logger.error("updateShowArtWork", "$response")
                                }

                                is ApiResponse.Success -> {
                                    imageCache.insert(
                                        Show_image(
                                            trakt_id = show.trakt_id,
                                            tmdb_id = tmdbId,
                                            poster_url = response.body.posterPath?.let {
                                                formatterUtil.formatTmdbPosterPath(it)
                                            },
                                            backdrop_url = response.body.backdropPath?.let {
                                                formatterUtil.formatTmdbPosterPath(it)
                                            },
                                        ),
                                    )

                                    requestManagerRepository.insert(
                                        LastRequest(
                                            id = tmdbId,
                                            entityId = show.trakt_id,
                                            requestType = "SHOW_ARTWORK",
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }

                Either.Right(Unit)
            }
            .catch { Either.Left(DefaultError(exceptionHandler.resolveError(it))) }
            .flowOn(dispatchers.io)
}
