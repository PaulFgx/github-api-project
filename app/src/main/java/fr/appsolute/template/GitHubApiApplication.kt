package fr.appsolute.template

import android.app.Application
//import fr.appsolute.template.data.database.DatabaseManager

/**
 * New entry point of the application (Referenced in the manifests)
 */
class GitHubApiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDatabase()
    }

    // Init the database access
    private fun initDatabase() {
        //DatabaseManager.getInstance(this)
    }
}