package com.example.percobaan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import com.example.percobaan.repositori.RepositoriSiswa
import com.example.percobaan.room.MataKuliah
import com.example.percobaan.room.Siswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class EntryViewModel(
    private val repositoriSiswa: RepositoriSiswa,
    private val repositoriMataKuliah: RepositoriMataKuliah
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // BARU: Expose daftar Mata Kuliah
    val mataKuliahList: StateFlow<List<MataKuliah>> =
        repositoriMataKuliah.getAllMataKuliahStream("%")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    /** VALIDASI INPUT */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank() &&
                    kelas.isNotBlank() &&
                    tanggal_lahir.isNotBlank() &&
                    id_matkul > 0 // BARU: Validasi id_matkul
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
    val telpon: String = "",
    val id_matkul: Int = 0      // ← TAMBAH INI

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
        telpon = telpon.trim(),
        id_matkul = id_matkul       // ← ini yang benar
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
        telpon = telpon,
        id_matkul = id_matkul       // ← jangan lupa

    )

/** Entity → UIState */
fun Siswa.toUIStateSiswa(isEntryValid: Boolean = false): UIStateSiswa =
    UIStateSiswa(
        detailSiswa = this.toDetailSiswa(),
        isEntryValid = isEntryValid
    )