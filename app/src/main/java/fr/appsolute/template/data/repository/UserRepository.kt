package fr.appsolute.template.data.repository

import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.networking.HttpClientManager
import fr.appsolute.template.data.networking.api.UserApi
import fr.appsolute.template.data.networking.createApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun getAllUsers(): List<User>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.loadListUsers()
                check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
                response.body() ?: throw IllegalStateException("Body is null")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun getUserDetails(url: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getUserDetails(url)
                check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
                response.body() ?: throw IllegalStateException("Body is null")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

interface UserRepository {

    suspend fun getAllUsers(): List<User>?

    suspend fun getUserDetails(url: String): User?

    companion object {

        val instance: UserRepository by lazy {

            UserRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }
}