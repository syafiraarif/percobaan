package com.example.percobaan.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: User)

    // cek user berdasarkan username dan password
    @Query("SELECT * FROM tblUser WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    // cek username sudah ada atau belum
    @Query("SELECT COUNT(*) FROM tblUser WHERE username = :username")
    suspend fun checkUsername(username: String): Int
}
