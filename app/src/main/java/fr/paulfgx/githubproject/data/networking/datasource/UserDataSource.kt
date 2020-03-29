package fr.paulfgx.githubproject.data.networking.datasource

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import fr.paulfgx.githubproject.data.model.User
import fr.paulfgx.githubproject.data.networking.api.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * DataSource use for the paginated api ([UserApi.getAllUser])
 */
class UserDataSource private constructor(
    private val api: UserApi,
    private val scope: CoroutineScope,
    private val token: String
) : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUser(token, FIRST_KEY).run {

                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response,
                    0,
                    response.size, // total size not known
                    null,
                    if (response.isNotEmpty()) response.last().id else null
                ) else callback.onResult(
                    response,
                    null,
                    if (response.isNotEmpty()) response.last().id else null
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.getAllUser(token, params.key).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                callback.onResult(
                    response,
                    if (response.isNotEmpty()) response.last().id else null
                )
            } catch (e: Exception) {
                Log.e(TAG, "loadInitial: ", e)
            }
        }
    }

    // This method will not be used in this app
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    class Factory(
        private val api: UserApi,
        private val scope: CoroutineScope,
        private val token: String
    ) : DataSource.Factory<Int, User>() {
        override fun create(): DataSource<Int, User> =
            UserDataSource(
                api, scope, token
            )
    }

    companion object {
        private const val TAG: String = "UserDataSource"
        private const val FIRST_KEY = 0
    }

}