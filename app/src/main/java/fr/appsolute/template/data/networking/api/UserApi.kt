package fr.appsolute.template.data.networking.api

import fr.appsolute.template.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface UserApi {

    // https://api.github.com/users?since=0
    /*@GET(GET_ALL_USER_PATH_PAGINATED)
    suspend fun getAllUsersPaginated(
        @Path("id") id: Int
    ): Response<List<User>>*/

    @GET(GET_ALL_USER_PATH)
    suspend fun getAllUser(
        @Query("since") id: Int
    ): Response<List<User>>

    @GET(GET_ALL_USER_PATH)
    suspend fun loadListUsers(): Response<List<User>>

    companion object {
        const val GET_ALL_USER_PATH = "users"
        const val GET_USER_DETAILS = "users/{id}"
        const val GET_ALL_USER_PATH_PAGINATED = "users?since={id}"
    }

    @GET
    suspend fun getUserDetails(@Url url: String?): Response<User>
}