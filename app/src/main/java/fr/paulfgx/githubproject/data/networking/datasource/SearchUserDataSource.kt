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
 * DataSource use for the paginated api ([UserApi.searchUsers])
 */
class SearchUserDataSource private constructor(
    private val api: UserApi,
    private val scope: CoroutineScope,
    private val query: String,
    private val sort: String,
    private val token: String
) : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.searchAndSortUsers(query = query, page = FIRST_KEY, perPage = PER_PAGE, sort = sort, token = token).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                if (params.placeholdersEnabled) callback.onResult(
                    response.items,
                    0,
                    calculatePageCount(response.total_count),
                    null,
                    FIRST_KEY + 1
                ) else callback.onResult(
                    response.items,
                    null,
                    FIRST_KEY + 1
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = api.searchAndSortUsers(query = query, page = params.key, perPage = PER_PAGE, sort = sort, token = token).run {
                    if (this.isSuccessful) this.body()
                        ?: throw IllegalStateException("Body is null")
                    else throw IllegalStateException("Response is not successful : code = ${this.code()}")
                }
                callback.onResult(
                    response.items,
                    params.key + 1
                )
            } catch (e: Exception) {
                Log.e(TAG, "loadInitial: ", e)
            }
        }
    }

    // This method will not be used in this app
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) = Unit

    // Adding the first key (1) because call with page=0 and page=1 returns the same results
    private fun calculatePageCount(totalItemCount: Int) = (totalItemCount / PER_PAGE) + FIRST_KEY

    class Factory(
        private val api: UserApi,
        private val scope: CoroutineScope,
        private val query: String,
        private val sort: String,
        private val token: String
    ) : DataSource.Factory<Int, User>() {
        override fun create(): DataSource<Int, User> =
            SearchUserDataSource(
                api, scope, query, sort, token
            )
    }

    companion object {
        private const val TAG: String = "SearchUserDataSource"
        private const val FIRST_KEY = 1
        private const val PER_PAGE = 30
    }
}