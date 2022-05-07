package com.example.aplikasigit3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aplikasigit3.User
import com.example.aplikasigit3.database.UserDao
import com.example.aplikasigit3.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> = mUsersDao.getAllUsers()
    fun insert(note: User) {
        executorService.execute { mUsersDao.insert(note) }
    }

    fun delete(note: User) {
        executorService.execute { mUsersDao.delete(note) }
    }
}