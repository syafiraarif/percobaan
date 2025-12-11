package com.example.percobaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblMataKuliah")
data class MataKuliah(
    @PrimaryKey(autoGenerate = true)
    val id_matkul: Int = 0,
    val nama_matkul: String
)
