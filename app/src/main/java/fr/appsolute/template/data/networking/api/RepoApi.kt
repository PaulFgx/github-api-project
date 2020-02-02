package fr.appsolute.template.data.networking.api

import fr.appsolute.template.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RepoApi {

    /**
     * Suspended function to get all repositories of an user
     */
    @GET
    suspend fun getReposWithUrl(@Url url: String) : Response<List<Repo>>
}