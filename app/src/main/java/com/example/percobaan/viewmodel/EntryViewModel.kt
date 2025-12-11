package com.example.percobaan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.percobaan.repositori.RepositoriSiswa
import com.example.percobaan.room.Siswa


class EntryViewModel(private val repositoriSiswa: RepositoriSiswa) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /** VALIDASI INPUT */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank() &&
                    kelas.isNotBlank() &&
                    tanggal_lahir.isNotBlank()
        }
    }

    /** UPDATE UI STATE */
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /** SIMPAN DATA */
    suspend fun saveSiswa() {
        if (validasiInput()) {
            repositoriSiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}

/* ================================
   DATA STATE
   ================================ */

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)

/** MODEL UNTUK FORM */
data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val peminatan: List<String> = emptyList(),  // <- List untuk UI Checkbox
    val tanggal_lahir: String = "",
    val telpon: String = ""
)

/* ================================
   KONVERSI MODEL
   ================================ */

/** DetailSiswa → Entity Siswa */
fun DetailSiswa.toSiswa(): Siswa =
    Siswa(
        id = id,
        nama = nama.trim(),
        alamat = alamat.trim(),
        kelas = kelas.trim(),
        peminatan = peminatan.joinToString(","),  // Convert List → String CSV
        tanggal_lahir = tanggal_lahir.trim(),
        telpon = telpon.trim()
    )

/** Entity → DetailSiswa */
fun Siswa.toDetailSiswa(): DetailSiswa =
    DetailSiswa(
        id = id,
        nama = nama,
        alamat = alamat,
        kelas = kelas,
        peminatan = if (peminatan.isBlank()) emptyList()
        else peminatan.split(",").map { it.trim() },
        tanggal_lahir = tanggal_lahir,
        telpon = telpon
    )

/** Entity → UIState */
fun Siswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa =
    UIStateSiswa(
        detailSiswa = this.toDetailSiswa(),
        isEntryValid = isEntryValid
    )

