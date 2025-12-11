package com.example.percobaan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriSiswa
import com.example.percobaan.room.Siswa
import kotlinx.coroutines.flow.*

class HomeViewModel(private val repositoriSiswa: RepositoriSiswa): ViewModel()
{
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Tambahkan variabel state untuk input pencarian
    var searchQuery by mutableStateOf("")
        private set

    // Tambahkan fungsi untuk memperbarui state pencarian
    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }

    val homeUiState: StateFlow<HomeUiState> =
        // Mengubah alur data setiap kali searchQuery berubah
        snapshotFlow { searchQuery }
            .flatMapLatest { query ->
                // Membuat query SQL dengan wildcard untuk pencarian parsial
                val sqlQuery = "%$query%"
                repositoriSiswa.getAllSiswaStream(sqlQuery)
                    .map { HomeUiState(listSiswa = it.toList()) }
            }
            .stateIn(scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState())
    data class HomeUiState(
        val listSiswa: List<Siswa> = listOf()
    )

}
