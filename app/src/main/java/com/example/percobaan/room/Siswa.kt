package com.example.percobaan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblSiswa")
data class Siswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val alamat: String,
    val kelas: String,
    val peminatan: String,
    val tanggal_lahir: String,
    val telpon: String
)
