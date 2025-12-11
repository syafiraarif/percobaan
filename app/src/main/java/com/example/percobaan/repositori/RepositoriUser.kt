package com.example.percobaan.repositori

import com.example.percobaan.room.User
import com.example.percobaan.room.UserDao

interface RepositoriUser {
    suspend fun register(user: User)
    suspend fun login(username: String, password: String): User?
    suspend fun checkUsername(username: String): Int
}

class OfflineRepositoriUser(
    private val userDao: UserDao
): RepositoriUser {

    override suspend fun register(user: User) =
        userDao.register(user)

    override suspend fun login(username: String, password: String): User? =
        userDao.login(username, password)

    override suspend fun checkUsername(username: String): Int =
        userDao.checkUsername(username)
}
