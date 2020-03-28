package fr.paulfgx.githubproject.ui.utils

/**
 * We can add properties to enum values, and then access them like any other property
 */
enum class SortUserType(val apiKeyword: String) {
    CREATION_DATE(apiKeyword = "joined"),
    NB_REPOS(apiKeyword = "repositories"),
    NB_FOLLOWERS(apiKeyword = "followers"),
    NONE(apiKeyword = "")
}