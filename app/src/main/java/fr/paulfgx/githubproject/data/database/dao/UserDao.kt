package fr.paulfgx.githubproject.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.paulfgx.githubproject.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun selectAll(): List<User>
}