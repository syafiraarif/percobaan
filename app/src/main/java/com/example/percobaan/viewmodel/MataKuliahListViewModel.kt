package com.example.percobaan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import com.example.percobaan.room.MataKuliah
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MataKuliahListViewModel(
    private val repo: RepositoriMataKuliah
) : ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    // REMOVED: searchQuery state dan updateSearchQuery function

    // UPDATED: uiState langsung stream semua data (menggunakan "%" sebagai wildcard search)
    val uiState: StateFlow<MataKuliahListUiState> =
        repo.getAllMataKuliahStream("%") // Mengambil SEMUA data
            .map { MataKuliahListUiState(listMatkul = it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                MataKuliahListUiState()
            )

    fun delete(mk: MataKuliah) = viewModelScope.launch {
        repo.delete(mk)
    }
}

data class MataKuliahListUiState(
    val listMatkul: List<MataKuliah> = emptyList()
)