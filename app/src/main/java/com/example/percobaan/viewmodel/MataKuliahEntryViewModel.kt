package com.example.percobaan.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import com.example.percobaan.room.MataKuliah
import kotlinx.coroutines.launch


class MataKuliahEntryViewModel(
    private val repo: RepositoriMataKuliah
) : ViewModel() {

    var uiState by mutableStateOf(MataKuliahDetail())
        private set

    fun updateState(newState: MataKuliahDetail) {
        uiState = newState.copy(
            isValid = newState.nama_matkul.isNotBlank()
        )
    }

    fun insert(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) {
            repo.insert(uiState.toEntity())
            onDone()
        }
    }
}
