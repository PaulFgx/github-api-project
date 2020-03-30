package fr.paulfgx.githubproject.data.networking.api

import fr.paulfgx.githubproject.data.model.PaginatedResult
import fr.paulfgx.githubproject.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface UserApi {

    // For the paginated list
    @GET(GET_ALL_USER_PATH)
    suspend fun getAllUser(
        @Header("Authorization") token: String,
        @Query("since") id: Int
    ): Response<List<User>>

    // To get only the first page
    @GET(GET_ALL_USER_PATH)
    suspend fun loadListUsers(): Response<List<User>>

    // Search users that match the user input
    @GET(GET_SEARCH_USERS_PATH)
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<PaginatedResult<User>>

    // Search users and sort them
    @GET(GET_SEARCH_USERS_PATH)
    suspend fun searchAndSortUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Response<PaginatedResult<User>>

    @GET
    suspend fun getUserDetails(
        @Header("Authorization") token: String,
        @Url url: String?
    ): Response<User>

    companion object {
        const val GET_ALL_USER_PATH = "users"
        const val GET_SEARCH_USERS_PATH = "search/users"
    }
}