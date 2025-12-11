package com.example.percobaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblUser")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)

