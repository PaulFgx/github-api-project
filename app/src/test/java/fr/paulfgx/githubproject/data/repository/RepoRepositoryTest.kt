package fr.paulfgx.githubproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.paulfgx.githubproject.BuildConfig
import fr.paulfgx.githubproject.data.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.internal.matchers.apachecommons.ReflectionEquals

class RepoRepositoryTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    private val testDispatcher = newSingleThreadContext("UI context")
    private lateinit var repository: RepoRepository
    private val apiToken = BuildConfig.GITHUB_API_TOKEN

    private val mojomboFirstRepo = Repo(

        id = 26899533,
        node_id = "MDEwOlJlcG9zaXRvcnkyNjg5OTUzMw==",
        name = "30daysoflaptops.github.io",
        full_name = "mojombo/30daysoflaptops.github.io",
        private = false,
        owner = Repo.Owner(
            "mojombo",
            1,
            "MDQ6VXNlcjE=",
            "https://avatars0.githubusercontent.com/u/1?v=4",
            "",
            "https://api.github.com/users/mojombo",
            "https://github.com/mojombo",
            "https://api.github.com/users/mojombo/followers",
            "https://api.github.com/users/mojombo/following{/other_user}",
            "https://api.github.com/users/mojombo/gists{/gist_id}",
            "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
            "https://api.github.com/users/mojombo/subscriptions",
            "https://api.github.com/users/mojombo/orgs",
            "https://api.github.com/users/mojombo/repos",
            "https://api.github.com/users/mojombo/events{/privacy}",
            "https://api.github.com/users/mojombo/received_events",
            "User",
            false
        ),
        html_url = "https://github.com/mojombo/30daysoflaptops.github.io",
        description = null,
        fork = true,
        url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io",
        forks_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/forks",
        keys_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/keys{/key_id}",
        collaborators_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/collaborators{/collaborator}",
        teams_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/teams",
        hooks_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/hooks",
        issue_events_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/issues/events{/number}",
        events_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/events",
        assignees_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/assignees{/user}",
        branches_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/branches{/branch}",
        tags_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/tags",
        blobs_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/git/blobs{/sha}",
        gits_tags_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/git/tags{/sha}",
        git_refs_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/git/refs{/sha}",
        trees_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/git/trees{/sha}",
        statuses_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/statuses/{sha}",
        languages_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/languages",
        stargazers_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/stargazers",
        contributors_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/contributors",
        subscribers_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/subscribers",
        subscription_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/subscription",
        commits_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/commits{/sha}",
        git_commits_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/git/commits{/sha}",
        comments_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/comments{/number}",
        issue_comment_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/issues/comments{/number}",
        contents_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/contents/{+path}",
        compare_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/compare/{base}...{head}",
        merges_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/merges",
        archive_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/{archive_format}{/ref}",
        downloads_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/downloads",
        issues_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/issues{/number}",
        pulls_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/pulls{/number}",
        milestones_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/milestones{/number}",
        notifications_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/notifications{?since,all,participating}",
        labels_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/labels{/name}",
        releases_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/releases{/id}",
        deployments_url = "https://api.github.com/repos/mojombo/30daysoflaptops.github.io/deployments",
        created_at = "2014-11-20T06:42:06Z",
        updated_at = "2019-08-13T15:55:54Z",
        pushed_at = "2014-11-20T06:42:47Z",
        git_url = "git://github.com/mojombo/30daysoflaptops.github.io.git",
        ssh_url = "git@github.com:mojombo/30daysoflaptops.github.io.git",
        clone_url = "https://github.com/mojombo/30daysoflaptops.github.io.git",
        svn_url = "https://github.com/mojombo/30daysoflaptops.github.io",
        homepage = null,
        size = 1197,
        stargazers_count = 5,
        watchers_count = 5,
        language = "CSS",
        has_issues = false,
        has_projects = true,
        has_downloads = true,
        has_wiki = true,
        has_pages = false,
        forks_count = 2,
        mirror_url = null,
        archived = false,
        disabled = false,
        open_issues_count = 0,
        licence = null,
        forks = 2,
        open_issues = 0,
        watchers = 5,
        default_branch = "gh-pages"
    )

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = RepoRepository.instance
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.close()
    }

    @Test
    fun getReposWithUrl() = runBlocking {

        val repoReference = mojomboFirstRepo
        val value = repository.getReposWithUrl("https://api.github.com/users/mojombo/repos", apiToken)

        // test that exclude some fields to avoid unit test crash when remote informations are updated
        assertTrue(
            ReflectionEquals(
                repoReference,
                "description",
                "fork",
                "private",
                "size",
                "stargazers_count",
                "watchers_count",
                "has_issues",
                "has_projects",
                "has_downloads",
                "has_wiki",
                "has_pages",
                "forks_count",
                "mirror_url",
                "archived",
                "disabled",
                "open_issues_count",
                "licence",
                "forks",
                "open_issues",
                "watchers",
                "default_branch",
                "updated_at",
                "pushed_at"
            ).matches(value?.first())
        )
    }
}