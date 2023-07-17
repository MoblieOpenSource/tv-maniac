package com.thomaskioko.tvmaniac.presentation.discover

import com.freeletics.flowredux.dsl.ChangedState
import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.State
import com.thomaskioko.tvmaniac.category.api.model.Category
import com.thomaskioko.tvmaniac.showimages.api.ShowImagesRepository
import com.thomaskioko.tvmaniac.shows.api.DiscoverRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import me.tatarka.inject.annotations.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@Inject
class DiscoverStateMachine(
    private val discoverRepository: DiscoverRepository,
    private val showImagesRepository: ShowImagesRepository,
) : FlowReduxStateMachine<DiscoverState, ShowsAction>(initialState = Loading) {

    init {
        spec {
            inState<Loading> {
                onEnter { state ->
                    fetchShowData(state)
                }
            }

            inState<DataLoaded> {
                collectWhileInState(observeShowData()) { result, state ->
                    state.mutate {
                        copy(
                            recommendedShows = result.recommendedShows,
                            trendingShows = result.trendingShows,
                            popularShows = result.popularShows,
                            anticipatedShows = result.anticipatedShows,
                            errorMessage = result.errorMessage,
                            isContentEmpty = result.isContentEmpty,
                        )
                    }
                }

                collectWhileInStateEffect(showImagesRepository.updateShowArtWork()) { _, _ ->
                    /** No need to do anything. Just trigger artwork download. **/
                }

                on<ReloadCategory> { _, state ->
                    // TODO:: Implement reloading category data
                    state.noChange()
                }

                on<RetryLoading> { _, state ->
                    state.override { Loading }
                }
            }
        }
    }

    private suspend fun fetchShowData(state: State<Loading>): ChangedState<DiscoverState> {
        val trendingResponse = discoverRepository.fetchShows(Category.TRENDING)
        val recommendedResponse = discoverRepository.fetchShows(Category.RECOMMENDED)
        val popularResponse = discoverRepository.fetchShows(Category.POPULAR)
        val anticipatedResponse = discoverRepository.fetchShows(Category.ANTICIPATED)

        return state.override {
            DataLoaded(
                trendingShows = trendingResponse.toTvShowList(),
                popularShows = popularResponse.toTvShowList(),
                anticipatedShows = anticipatedResponse.toTvShowList(),
                recommendedShows = recommendedResponse.take(5).toTvShowList(),
            )
        }
    }

    private fun observeShowData(): Flow<DataLoaded> =
        combine(
            discoverRepository.observeTrendingShows(),
            discoverRepository.observePopularShows(),
            discoverRepository.observeAnticipatedShows(),
            discoverRepository.observeRecommendedShows(),
        ) { trending, popular, anticipated, featured ->
            toShowResultState(trending, popular, anticipated, featured)
        }
}
