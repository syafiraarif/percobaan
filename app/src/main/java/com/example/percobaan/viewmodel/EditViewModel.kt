// app/src/main/java/com/example/percobaan/viewmodel/EditViewModel.kt
package com.example.percobaan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import com.example.percobaan.repositori.RepositoriSiswa
import com.example.percobaan.room.MataKuliah
import com.example.percobaan.view.route.DestinasiDetailSiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa,
    private val repositoriMataKuliah: RepositoriMataKuliah
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // FIX: Menggunakan safe retrieval (default ke 0 jika argumen tidak ada)
    private val idSiswa: Int = savedStateHandle[DestinasiDetailSiswa.itemIdArg] ?: 0

    // Expose daftar Mata Kuliah
    val mataKuliahList: StateFlow<List<MataKuliah>> =
        // [FIXED] Hapus parameter "%"
        repositoriMataKuliah.getAllMataKuliahStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        // Load pertama kali
        viewModelScope.launch {
            if (idSiswa > 0) { // Hanya jalankan jika ID valid (> 0)
                uiStateSiswa = repositoriSiswa.getSiswaStream(idSiswa)
                    .filterNotNull()
                    .first()
                    .toUIStateSiswa(true)
            }
        }
    }

    /** Fungsi tambahan untuk EditSiswaScreen (LaunchedEffect) */
    fun loadSiswa(id: Int) {
        viewModelScope.launch {
            uiStateSiswa = repositoriSiswa.getSiswaStream(id)
                .filterNotNull()
                .first()
                .toUIStateSiswa(true)
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank()
        }
    }

    /** UPDATE DATA */
    suspend fun updateSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            repositoriSiswa.updateSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}