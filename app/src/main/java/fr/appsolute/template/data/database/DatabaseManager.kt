package fr.appsolute.template.data.database

import androidx.room.Room
import fr.appsolute.template.GitHubApiApplication

private class DatabaseManagerImpl(
    override val database: GitHubDatabase
) : DatabaseManager

interface DatabaseManager {

    val database: GitHubDatabase

    companion object {
        private const val DATABASE_NAME = "github.db"

        @Volatile
        private var databaseManager: DatabaseManager? = null

        var override: DatabaseManager? = null

        fun getInstance(app: GitHubApiApplication? = null): DatabaseManager {
            return override ?: databaseManager ?: synchronized(this) {
                DatabaseManagerImpl(
                    Room.databaseBuilder(
                        app ?: throw IllegalStateException("No Application"),
                        GitHubDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                ).also {
                    databaseManager = it
                }
            }
        }

    }
}