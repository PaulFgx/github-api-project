package fr.paulfgx.githubproject

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import fr.paulfgx.githubproject.data.database.DatabaseManager

/**
 * New entry point of the application (Referenced in the manifests)
 */
class GitHubApiApplication : Application() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        initDatabase()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    // Init the database access
    private fun initDatabase() {
        DatabaseManager.getInstance(this)
    }
}