// app/src/main/java/com/example/percobaan/viewmodel/MataKuliahListViewModel.kt
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

    // UPDATED: uiState langsung stream semua data
    val uiState: StateFlow<MataKuliahListUiState> =
        // [FIXED] Panggil tanpa parameter
        repo.getAllMataKuliahStream()
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