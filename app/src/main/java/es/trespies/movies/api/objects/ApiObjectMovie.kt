package es.trespies.movies.api.objects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiObjectMovie(var id: String?,
                          @SerializedName("imdb_id")
                          var imdbId: String?,
                          var title: String?,
                          @SerializedName("original_title")
                          var originalTitle: String?,
                          @SerializedName("original_language")
                          var originalLang: String?,
                          var overview: String?,

                          @SerializedName("vote_average")
                          var voteAverage: Float?,
                          @SerializedName("vote_count")
                          var voteCount: Long?,

                          @SerializedName("poster_path")
                          var posterPath: String?,
                          @SerializedName("release_date")
                          var releaseDate: String?,
                          var popularity: Float?
/*
                          @SerializedName("poster_path")
                          var posterPath: String?,
                          @SerializedName("release_date")
                          var releaseDate: String?,
                          var popularity: Long?

 */
): Serializable
