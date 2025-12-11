package com.example.percobaan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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

    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }

    val uiState: StateFlow<MataKuliahListUiState> =
        snapshotFlow { searchQuery }
            .flatMapLatest { query ->
                val filteredQuery = "%$query%"
                repo.getAllMataKuliahStream(filteredQuery)
                    .map { MataKuliahListUiState(listMatkul = it) }
            }
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