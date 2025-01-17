package com.thomaskioko.tvmaniac.presentation.showdetails

import com.thomaskioko.tvmaniac.core.db.ShowById
import com.thomaskioko.tvmaniac.core.db.SimilarShows
import com.thomaskioko.tvmaniac.presentation.showdetails.model.Season
import com.thomaskioko.tvmaniac.presentation.showdetails.model.Show
import com.thomaskioko.tvmaniac.presentation.showdetails.model.Trailer
import com.thomaskioko.tvmaniac.core.db.Seasons as SeasonCache

val show = Show(
    traktId = 84958,
    tmdbId = 849583,
    title = "Loki",
    overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
        "an alternate version of Loki is brought to the mysterious Time Variance " +
        "Authority, a bureaucratic organization that exists outside of time and " +
        "space and monitors the timeline. They give Loki a choice: face being " +
        "erased from existence due to being a “time variant”or help fix " +
        "the timeline and stop a greater threat.",
    language = "en",
    votes = 4958,
    rating = 8.1,
    genres = listOf("Horror", "Action"),
    status = "Returning Series",
    year = "2024",
    posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
    backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
)

val similarShows = listOf(
    Show(
        traktId = 184958,
        tmdbId = 284958,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        language = "en",
        votes = 4958,
        rating = 8.1,
        genres = listOf("Horror", "Action"),
        status = "Returning Series",
        year = "2024",
        posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
    ),
)

val showDetailsLoaded = ShowDetailsLoaded(
    show = show,
    seasonsContent = ShowDetailsLoaded.SeasonsContent(
        isLoading = true,
        seasonsList = emptyList(),
    ),
    similarShowsContent = ShowDetailsLoaded.SimilarShowsContent(
        isLoading = true,
        similarShows = emptyList(),
    ),
    trailersContent = ShowDetailsLoaded.TrailersContent(
        isLoading = true,
        hasWebViewInstalled = false,
        playerErrorMessage = null,
        trailersList = emptyList(),
    ),
    errorMessage = null,
)
val seasonsShowDetailsLoaded = ShowDetailsLoaded.SeasonsContent(
    isLoading = false,
    seasonsList = listOf(
        Season(
            seasonId = 84958,
            tvShowId = 114355,
            name = "Season 1",
        ),
    ),
)

val trailerShowDetailsLoaded = ShowDetailsLoaded.TrailersContent(
    isLoading = false,
    hasWebViewInstalled = false,
    playerErrorMessage = null,
    trailersList = listOf(
        Trailer(
            showId = 84958,
            key = "Fd43V",
            name = "Some title",
            youtubeThumbnailUrl = "https://i.ytimg.com/vi/Fd43V/hqdefault.jpg",
        ),
    ),
)

val similarShowLoaded = ShowDetailsLoaded.SimilarShowsContent(
    isLoading = false,
    similarShows = similarShows,
)

val selectedShow = ShowById(
    trakt_id = 84958,
    tmdb_id = 849583,
    title = "Loki",
    overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
        "an alternate version of Loki is brought to the mysterious Time Variance " +
        "Authority, a bureaucratic organization that exists outside of time and " +
        "space and monitors the timeline. They give Loki a choice: face being " +
        "erased from existence due to being a “time variant”or help fix " +
        "the timeline and stop a greater threat.",
    language = "en",
    votes = 4958,
    rating = 8.1,
    genres = listOf("Horror", "Action"),
    status = "Returning Series",
    year = "2024",
    runtime = 45,
    poster_url = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
    backdrop_url = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
    aired_episodes = 12,
    trakt_id_ = 1234,
    id = 12345,
    created_at = null,
    synced = false,
    tmdb_id_ = 1232,
)
val similarShowResult = listOf(
    SimilarShows(
        trakt_id = 184958,
        tmdb_id = 284958,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        language = "en",
        votes = 4958,
        rating = 8.1,
        genres = listOf("Horror", "Action"),
        status = "Returning Series",
        year = "2024",
        runtime = 45,
        poster_url = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdrop_url = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
    ),
)

val seasons = listOf(
    SeasonCache(
        id = 84958,
        show_trakt_id = 114355,
        name = "Season 1",
        episode_count = 10,
        season_number = 1,
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
    ),
)
