package fr.appsolute.template.data.repository

import fr.appsolute.template.data.model.Repo
import fr.appsolute.template.data.networking.HttpClientManager
import fr.appsolute.template.data.networking.api.RepoApi
import fr.appsolute.template.data.networking.createApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class RepoRepositoryImpl(
    private val api: RepoApi
) : RepoRepository {

    override suspend fun getReposWithUrl(url: String): List<Repo>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getReposWithUrl(url)
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

    suspend fun getReposWithUrl(url: String) : List<Repo>?

    companion object {
        val instance: RepoRepository by lazy {
            RepoRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }
}