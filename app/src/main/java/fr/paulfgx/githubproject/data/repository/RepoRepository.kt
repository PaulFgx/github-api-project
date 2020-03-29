package fr.paulfgx.githubproject.data.repository

import fr.paulfgx.githubproject.data.model.Repo
import fr.paulfgx.githubproject.data.networking.HttpClientManager
import fr.paulfgx.githubproject.data.networking.api.RepoApi
import fr.paulfgx.githubproject.data.networking.createApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class RepoRepositoryImpl(
    private val api: RepoApi
) : RepoRepository {

    override suspend fun getReposWithUrl(url: String, token: String): List<Repo>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getReposWithUrl(token, url)
                check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
                response.body() ?: throw IllegalStateException("Body is null")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

interface RepoRepository {

    suspend fun getReposWithUrl(url: String, token: String) : List<Repo>?

    companion object {
        val instance: RepoRepository by lazy {
            RepoRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }
}