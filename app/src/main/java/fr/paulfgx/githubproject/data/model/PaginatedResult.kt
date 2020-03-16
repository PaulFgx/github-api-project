package fr.paulfgx.githubproject.data.model

import com.google.gson.annotations.SerializedName

data class PaginatedResult<T>(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("incomplete_results") val incomplete_results: Boolean,
    @SerializedName("items") val items: List<T>
)