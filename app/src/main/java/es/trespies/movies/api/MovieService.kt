package es.trespies.movies.api

import es.trespies.movies.api.objects.ApiObjectMovie
import es.trespies.movies.api.objects.ApiObjectPaginator
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("${ENDPOINT}popular")
    suspend fun popularMovies(): ApiResponse<ApiObjectPaginator<ApiObjectMovie>>

    @GET("$ENDPOINT{movieId}")
    suspend fun movie(@Path("movieId") movieId: String): ApiResponse<ApiObjectMovie>

}

private const val ENDPOINT = "3/movie/"