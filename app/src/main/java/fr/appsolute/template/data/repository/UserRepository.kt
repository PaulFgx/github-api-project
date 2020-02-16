package fr.appsolute.template.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.appsolute.template.data.database.DatabaseManager
import fr.appsolute.template.data.database.dao.UserDao
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.networking.HttpClientManager
import fr.appsolute.template.data.networking.api.UserApi
import fr.appsolute.template.data.networking.createApi
import fr.appsolute.template.data.networking.datasource.UserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class UserRepositoryImpl(
    private val api: UserApi,
    private val dao: UserDao
) : UserRepository {

    private val paginationConfig = PagedList.Config
        .Builder()
        // If you set true you will have to catch
        // the place holder case in the adapter
        .setEnablePlaceholders(false)
        .setPageSize(30)
        .build()

    override fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<User>> {
        return LivePagedListBuilder(
            UserDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }

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

    override suspend fun insertUser(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                dao.insert(user)
                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }
}

interface UserRepository {

    /**
     * Return a LiveData (Observable Design Pattern) of a Paged List of Character
     */
    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<User>>

    suspend fun getAllUsers(): List<User>?

    suspend fun getUserDetails(url: String): User?

    suspend fun insertUser(user: User): Boolean

    companion object {

        val instance: UserRepository by lazy {
            UserRepositoryImpl(
                HttpClientManager.instance.createApi(),
                DatabaseManager.getInstance().database.userDao
            )
        }
    }
}