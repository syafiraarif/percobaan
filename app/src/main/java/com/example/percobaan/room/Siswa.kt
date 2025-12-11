package com.example.percobaan.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tblSiswa",
    foreignKeys = [
        ForeignKey(
            entity = MataKuliah::class,
            parentColumns = ["id_matkul"],
            childColumns = ["id_matkul"],
            onDelete = ForeignKey.CASCADE // <-- kalau matkul dihapus, siswa ikut hilang
        )
    ]
)
data class Siswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nama: String,
    val alamat: String,
    val kelas: String,
    val peminatan: String,
    val tanggal_lahir: String,
    val telpon: String,

    val id_matkul: Int // <-- tambahkan ini
)
