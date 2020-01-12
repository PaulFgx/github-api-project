package fr.appsolute.template.data.networking.api

import fr.appsolute.template.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Definition of end points for Character Api
 */
interface UserApi {

    /**
     * Suspended function to get first page of users
     */
    @GET(GET_ALL_USER_PATH)
    suspend fun loadListUsers(): Response<List<User>>

    companion object {
        const val GET_ALL_USER_PATH = "users"
        const val GET_USER_DETAILS = "users/{id}"
    }

    /**
     * Suspended function to get user details
     */
    @GET
    suspend fun getUserDetails(@Url url: String?): Response<User>
}