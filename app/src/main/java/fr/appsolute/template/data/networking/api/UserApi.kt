package fr.appsolute.template.data.networking.api

import fr.appsolute.template.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface UserApi {

    // For the paginated list
    @GET(GET_ALL_USER_PATH)
    suspend fun getAllUser(
        @Query("since") id: Int
    ): Response<List<User>>

    // To get only the first page
    @GET(GET_ALL_USER_PATH)
    suspend fun loadListUsers(): Response<List<User>>

    @GET
    suspend fun getUserDetails(@Url url: String?): Response<User>

    companion object {
        const val GET_ALL_USER_PATH = "users"
    }

}