package fr.paulfgx.githubproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.paulfgx.githubproject.BuildConfig
import fr.paulfgx.githubproject.data.model.Repo
import fr.paulfgx.githubproject.data.repository.RepoRepository
import kotlinx.coroutines.launch

open class RepoViewModel(
    val repository: RepoRepository
) : ViewModel() {

    private val apiToken = BuildConfig.GITHUB_API_TOKEN

    fun getReposWithUrl(url: String, onSuccess: OnSuccess<List<Repo>>) {
        viewModelScope.launch {
            repository.getReposWithUrl(url, apiToken)?.run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepoViewModel(RepoRepository.instance) as T
        }
    }
}