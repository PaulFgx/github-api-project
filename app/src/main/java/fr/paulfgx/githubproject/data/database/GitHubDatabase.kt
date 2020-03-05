package fr.paulfgx.githubproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.paulfgx.githubproject.data.database.dao.UserDao
import fr.paulfgx.githubproject.data.model.User

@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = true
)
abstract class GitHubDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}