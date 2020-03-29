package fr.paulfgx.githubproject.data.networking.api

import fr.paulfgx.githubproject.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface RepoApi {

    /**
     * Suspended function to get all repositories of an user
     */
    @GET
    suspend fun getReposWithUrl(
        @Header("Authorization") token: String,
        @Url url: String
    ) : Response<List<Repo>>
}