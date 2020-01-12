package fr.appsolute.template.data.networking

import fr.appsolute.template.data.networking.api.UserApi
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClientManagerTest {

    private lateinit var instance: HttpClientManager

    @Before
    fun setUp() {
        instance = HttpClientManager.instance
    }

    @Test
    fun getRetrofit() {
        instance.retrofit.apply {
            assertEquals(
                "Should be the same base url",
                this.baseUrl().toString(),
                "https://api.github.com/"
            )
            assertNotNull(
                "Should be a GsonConverter Factory",
                this.converterFactories().firstOrNull { it is GsonConverterFactory }
            )
        }
    }

    @Test
    fun createApiTest() {
        instance.createApi<UserApi>().apply {
            assertTrue("It Succeed", true)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Create api test without interface as API`() {
        instance.createApi<Retrofit>()
    }
}