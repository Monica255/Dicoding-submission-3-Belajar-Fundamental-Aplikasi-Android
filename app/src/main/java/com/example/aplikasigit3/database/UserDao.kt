package com.example.aplikasigit3.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplikasigit3.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from User ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<User>>
}