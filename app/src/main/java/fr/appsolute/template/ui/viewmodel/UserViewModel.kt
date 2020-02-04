package fr.appsolute.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.repository.UserRepository
import kotlinx.coroutines.launch

open class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _data = mutableListOf<Int>()

    val data: List<Int>
        get() = _data

    /**
     *  Return the paginated list of character from the API
     */
    val usersPagedList = repository.getPaginatedList(viewModelScope)

    /**
     * Call the api to fetch the details of a character from its ID
     */
    fun getUserWithUrl(url: String, onSuccess: OnSuccess<User>) {
        viewModelScope.launch {
            repository.getUserDetails(url)?.run(onSuccess)
        }
    }

    fun getAllUsers(onSuccess: OnSuccess<List<User>>) {
        viewModelScope.launch {
            repository.getAllUsers()?.run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(UserRepository.instance) as T
        }
    }
}