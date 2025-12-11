package com.example.percobaan.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import kotlinx.coroutines.launch

class MataKuliahEditViewModel(
    private val repo: RepositoriMataKuliah
) : ViewModel() {

    var uiState by mutableStateOf(MataKuliahDetail())
        private set

    fun load(id: Int) = viewModelScope.launch {
        repo.getMataKuliah(id).collect { mk ->
            uiState = mk.toDetail()
        }
    }

    fun update(onDone: () -> Unit = {}) = viewModelScope.launch {
        if (uiState.isValid) repo.update(uiState.toEntity())
        onDone()
    }
}
