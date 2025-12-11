package com.example.percobaan.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriMataKuliah
import com.example.percobaan.view.route.DetailMataKuliah
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repo: RepositoriMataKuliah
) : ViewModel() {

    // FIX: Menggunakan safe retrieval (default ke 0 jika argumen tidak ada)
    private val idMataKuliah: Int = savedStateHandle[DetailMataKuliah.argId] ?: 0

    val uiState: StateFlow<MataKuliahDetailUiState> =
        repo.getMataKuliah(idMataKuliah)
            .filterNotNull()
            .map {
                MataKuliahDetailUiState(detail = it.toDetail())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MataKuliahDetailUiState()
            )

    suspend fun deleteMataKuliah() {
        repo.delete(uiState.value.detail.toEntity())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MataKuliahDetailUiState(
    val detail: MataKuliahDetail = MataKuliahDetail()
)