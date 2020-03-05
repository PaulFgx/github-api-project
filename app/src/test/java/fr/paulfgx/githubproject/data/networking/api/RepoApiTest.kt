package fr.paulfgx.githubproject.data.networking.api

import fr.paulfgx.githubproject.data.model.Repo
import fr.paulfgx.githubproject.data.model.Repo.*
import fr.paulfgx.githubproject.data.networking.HttpClientManager
import fr.paulfgx.githubproject.data.networking.createApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import java.lang.IllegalStateException

class RepoApiTest {

    private lateinit var instance: HttpClientManager
    private lateinit var api: RepoApi

    private val mojomboFirstRepo = Repo(

        id = 26899533,
        node_id = "MDEwOlJlcG9zaXRvcnkyNjg5OTUzMw==",
        name = "30daysoflaptops.github.io",
        full_name = "mojombo/30daysoflaptops.github.io",
        private = false,
        owner = Owner(
            login = "mojombo",
            id = 1,
            node_id = "MDQ6VXNlcjE=",
            avatar_url = "https://avatars0.githubusercontent.com/u/1?v=4",
            gravatar_id = "",
            url = "https://api.github.com/users/mojombo",
            html_url = "https://github.com/mojombo",
            followers_url = "https://api.github.com/users/mojombo/followers",
            following_url = "https://api.github.com/users/mojombo/following{/other_user}",
            gists_url = "https://api.github.com/users/mojombo/gists{/gist_id}",
            starred_url = "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/mojombo/subscriptions",
            organizations_url = "https://api.github.com/users/mojombo/orgs",
            repos_url = "https://api.github.com/users/mojombo/repos",
            events_url = "https://api.github.com/users/mojombo/events{/privacy}",
            received_events_url = "https://api.github.com/users/mojombo/received_events",
            type = "User",
            site_admin = false
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

    @Before
    fun setUp() {
        instance = HttpClientManager.instance
        api = instance.createApi()
    }

    @Test
    fun getReposWithUrl() = runBlocking {
        val repoReference = mojomboFirstRepo

        api.getReposWithUrl("https://api.github.com/users/mojombo/repos").run {
            assertTrue("Request must be a success", this.isSuccessful)
            val response: List<Repo> =
                this.body() ?: throw IllegalStateException("Body is null")

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
                ).matches(response.first())
            )
        }
    }
}