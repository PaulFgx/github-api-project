package fr.appsolute.template.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.appsolute.template.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun selectAll(): List<User>

    @Query("SELECT COUNT(*) from user")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE id=:id")
    fun getById(id: Int) : User
}