package es.trespies.movies.repositories

import es.trespies.movies.api.objects.ApiObjectMovie
import es.trespies.movies.model.Movie
import es.trespies.movies.services.Configuration
import es.trespies.movies.util.DateFormarEnum
import es.trespies.movies.util.DateUtils

inline fun Movie.Companion.build(apiObject: ApiObjectMovie?, configuration: Configuration): Movie? {
    return apiObject?.let {
        it.id?.let { id -> Movie(id = id,
            imdbId = it.imdbId,
            title = it.title ?: "",
            originalTitle = it.originalTitle ?: "",
            originalLang = it.originalLang ?: "",
            overview = it.overview ?: "",
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            //Create full path for images
            posterPath = it.posterPath?.let { url -> "${configuration.urlImages}$url" },
            releaseDate = it.releaseDate,
            popularity = it.popularity,
            releaseDateTimestamp = DateUtils.stringToLong(it.releaseDate, DateFormarEnum.YYYYMMDD)
        ) }
    }
}