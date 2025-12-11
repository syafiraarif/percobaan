package com.example.percobaan.viewmodel

import com.example.percobaan.room.MataKuliah

data class MataKuliahDetail(
    val id_matkul: Int = 0,
    val nama_matkul: String = "",
    val isValid: Boolean = false
)

fun MataKuliah.toDetail() = MataKuliahDetail(
    id_matkul = id_matkul,
    nama_matkul = nama_matkul,
    isValid = true
)

fun MataKuliahDetail.toEntity() = MataKuliah(
    id_matkul = id_matkul,
    nama_matkul = nama_matkul
)
