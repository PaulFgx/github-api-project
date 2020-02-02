package fr.appsolute.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.template.data.model.Repo
import fr.appsolute.template.data.repository.RepoRepository
import kotlinx.coroutines.launch

open class RepoViewModel(
    val repository: RepoRepository
) : ViewModel() {

    fun getReposWithUrl(url: String, onSuccess: OnSuccess<List<Repo>>) {
        viewModelScope.launch {
            repository.getReposWithUrl(url)?.run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepoViewModel(RepoRepository.instance) as T
        }
    }
}