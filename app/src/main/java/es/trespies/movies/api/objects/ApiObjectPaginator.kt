package es.trespies.movies.api.objects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiObjectPaginator<T>(var page: Int?,
                                 @SerializedName("total_pages")
                                 var totalPages: Int?,
                                 @SerializedName("total_results")
                                 var totalResults: Long?,
                                 var results: List<T>?): Serializable
