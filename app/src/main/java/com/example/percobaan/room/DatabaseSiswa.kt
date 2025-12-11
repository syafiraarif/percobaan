package com.example.percobaan.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Siswa::class, User::class],
    version = 3,
    exportSchema = false
)
abstract class DatabaseSiswa : RoomDatabase() {

    abstract fun SiswaDao(): SiswaDao
    abstract fun UserDao(): UserDao

    companion object {
        @Volatile
        private var Instance: DatabaseSiswa? = null

        fun getDatabase(context: Context): DatabaseSiswa {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DatabaseSiswa::class.java,
                    "siswa_database"
                )
                    .fallbackToDestructiveMigration()   // untuk upgrade versi
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
