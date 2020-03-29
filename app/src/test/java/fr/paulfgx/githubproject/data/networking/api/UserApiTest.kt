package fr.paulfgx.githubproject.data.networking.api

import fr.paulfgx.githubproject.BuildConfig
import fr.paulfgx.githubproject.data.model.User
import fr.paulfgx.githubproject.data.networking.HttpClientManager
import fr.paulfgx.githubproject.data.networking.createApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals


class UserApiTest {

    private lateinit var instance: HttpClientManager
    private lateinit var api: UserApi
    private val apiToken = BuildConfig.GITHUB_API_TOKEN

    private val mojombo = User(
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
        site_admin = false,
        name = null,
        company = null,
        blog = null,
        location = null,
        email = null,
        hireable = null,
        bio = null,
        public_repos = null,
        public_gists = null,
        followers = null,
        following = null,
        created_at = null,
        updated_at = null
    )

    private val defunkt = User(
        login = "defunkt",
        id = 2,
        node_id = "MDQ6VXNlcjI=",
        avatar_url = "https://avatars0.githubusercontent.com/u/2?v=4",
        gravatar_id = "",
        url = "https://api.github.com/users/defunkt",
        html_url = "https://github.com/defunkt",
        followers_url = "https://api.github.com/users/defunkt/followers",
        following_url = "https://api.github.com/users/defunkt/following{/other_user}",
        gists_url = "https://api.github.com/users/defunkt/gists{/gist_id}",
        starred_url = "https://api.github.com/users/defunkt/starred{/owner}{/repo}",
        subscriptions_url = "https://api.github.com/users/defunkt/subscriptions",
        organizations_url = "https://api.github.com/users/defunkt/orgs",
        repos_url = "https://api.github.com/users/defunkt/repos",
        events_url = "https://api.github.com/users/defunkt/events{/privacy}",
        received_events_url = "https://api.github.com/users/defunkt/received_events",
        type = "User",
        site_admin = false,
        name = "Chris Wanstrath",
        company = null,
        blog = "http://chriswanstrath.com/",
        location = null,
        email = null,
        hireable = null,
        bio = "🍔",
        public_repos = 107,
        public_gists = 273,
        followers = 20863,
        following = 210,
        created_at = "2007-10-20T05:24:19Z",
        updated_at = "2019-11-01T21:56:00Z"
    )

    @Before
    fun setUp() {
        instance = HttpClientManager.instance
        api = instance.createApi()
    }

    @Test
    fun loadListUsers() = runBlocking {

        val firstUser = mojombo
        val count = 30

        api.loadListUsers().run {
            assertTrue("Request must be a success", this.isSuccessful)
            val data: List<User> =
                this.body() ?: throw IllegalStateException("Body is null")
            assertEquals(
                "Same count", count, data.size
            )
            assertEquals(
                "Fisrt User must be mojombo", firstUser, data.first()
            )
        }
    }

    @Test
    fun getAllUser() = runBlocking {

        val firstUser = mojombo
        val count = 30

        api.getAllUser(apiToken, 0).apply {
            assertTrue("Request must be a success", this.isSuccessful)
            val data: List<User> =
                this.body() ?: throw IllegalStateException("Body is null")
            assertEquals(
                "Same count", count, data.size
            )
            assertEquals(
                "Fisrt User must be mojombo", firstUser, data.first()
            )
            println("${this.body()?.size}")
        }
        
        return@runBlocking
    }

    @Test
    fun getUserDetails() = runBlocking {
        api.getUserDetails(apiToken, "https://api.github.com/users/defunkt").run {
            assertTrue("Request must be a success", this.isSuccessful)
            val data = this.body() ?: throw IllegalStateException("Body is null")

            // test that exclude some fields to avoid unit test crash when remote informations are updated
            assertTrue(ReflectionEquals(defunkt, "public_repos", "public_gists", "followers", "following", "updated_at").matches(data))
        }
    }
}