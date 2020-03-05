package fr.paulfgx.githubproject.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import fr.paulfgx.githubproject.data.database.DatabaseManager
import fr.paulfgx.githubproject.data.database.GitHubDatabase
import fr.paulfgx.githubproject.data.model.User
import fr.paulfgx.githubproject.test.getBlockingValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.internal.matchers.apachecommons.ReflectionEquals

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class UseRepositoryTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()
    private val testDispatcher = newSingleThreadContext("UI context")
    private lateinit var repository: UserRepository

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
        followers = 20862,
        following = 210,
        created_at = "2007-10-20T05:24:19Z",
        updated_at = "2019-11-01T21:56:00Z"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = UserRepository.instance
    }

    @After
    fun tearDown() {
        databaseManager.database.clearAllTables()
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacterDetails() = runBlocking {
        val data = repository.getUserDetails("https://api.github.com/users/defunkt")

        // test that exclude some fields to avoid unit test crash when remote informations are updated
        Assert.assertTrue(ReflectionEquals(defunkt, "public_repos", "public_gists", "followers", "following", "updated_at").matches(data))
    }

    @Test
    fun getPaginatedListTest() = runBlocking {
        val value = repository.getPaginatedList(this).getBlockingValue(
            timeOut = 10
        )
        Assert.assertTrue(
            "Size should be 0 or 30",
            value?.count()?.equals(0) ?: false || value?.count()?.equals(30) ?: false
        )
    }

    companion object {

        private lateinit var databaseManager: DatabaseManager

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            DatabaseManager.override = object : DatabaseManager {
                override val database: GitHubDatabase = Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    GitHubDatabase::class.java
                ).build()
            }.also {
                databaseManager = it
            }
        }
    }
}