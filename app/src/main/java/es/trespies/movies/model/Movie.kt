package es.trespies.movies.model

import androidx.room.Entity


@Entity(primaryKeys = ["id"])
data class Movie(var id: String,
                 var imdbId: String?,
                 var title: String,
                 var originalTitle: String,
                 var originalLang: String,
                 var overview: String,
                 var voteAverage: Float?,
                 var voteCount: Long?,
                 var posterPath: String?,
                 var releaseDate: String?,
                 var popularity: Float?,
                 var releaseDateTimestamp: Long?
                 ) {
    companion object {}
}
