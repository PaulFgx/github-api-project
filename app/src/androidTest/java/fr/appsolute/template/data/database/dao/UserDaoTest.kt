package fr.appsolute.template.data.database.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import fr.appsolute.template.data.database.GitHubDatabase
import fr.appsolute.template.data.model.User
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class UserDaoTest {

    // region mojombo
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
    // endregion

    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        dao = database.userDao
    }

    @Test
    fun selectAll() = runBlocking {
        dao.insert(mojombo)
        assertEquals("Should be same", mojombo, dao.selectAll().first())
    }

    companion object {
        private lateinit var database: GitHubDatabase

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                GitHubDatabase::class.java
            ).build()
        }
    }
}