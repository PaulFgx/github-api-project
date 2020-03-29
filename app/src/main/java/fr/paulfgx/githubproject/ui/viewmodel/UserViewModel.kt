package fr.paulfgx.githubproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.paulfgx.githubproject.data.model.User
import fr.paulfgx.githubproject.data.repository.UserRepository
import fr.paulfgx.githubproject.ui.utils.SortUserType
import kotlinx.coroutines.launch

open class UserViewModel(
    private val repository: UserRepository,
    var currentSortUserType: SortUserType = SortUserType.NONE
) : ViewModel() {

    private var _data = mutableListOf<Int>()

    val data: List<Int>
        get() = _data

    /**
     *  Return the paginated list of user from the API
     */
    val usersPagedList = repository.getPaginatedList(viewModelScope)

    /**
     *  Return the paginated list for the search feature
     */
    fun getSearchUsersPagedList(query: String) = repository.getSearchPaginatedList(viewModelScope, query, currentSortUserType.apiKeyword)

    /**
     * Call the api to fetch the details of a user from its ID
     */
    fun getUserWithUrl(url: String, onSuccess: OnSuccess<User>) {
        viewModelScope.launch {
            repository.getUserDetails(url)?.run(onSuccess)
        }
    }

    fun insertUser(user: User, onSuccess: OnSuccess<Boolean>) {
        viewModelScope.launch {
            repository.insertUser(user).run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(UserRepository.instance) as T
        }
    }
}