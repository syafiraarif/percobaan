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

    val uiState: StateFlow<List<MataKuliah>> =
        repo.getAllMataKuliah()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT),
                emptyList()
            )

    fun delete(mk: MataKuliah) = viewModelScope.launch {
        repo.delete(mk)
    }
}
