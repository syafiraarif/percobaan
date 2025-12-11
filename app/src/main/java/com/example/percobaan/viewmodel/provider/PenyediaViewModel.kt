package com.example.percobaan.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.percobaan.repositori.AplikasiSiswa
import com.example.percobaan.viewmodel.DetailViewModel
import com.example.percobaan.viewmodel.EditViewModel
import com.example.percobaan.viewmodel.EntryViewModel
import com.example.percobaan.viewmodel.HomeViewModel
import com.example.percobaan.viewmodel.MataKuliahEditViewModel
import com.example.percobaan.viewmodel.MataKuliahEntryViewModel
import com.example.percobaan.viewmodel.MataKuliahListViewModel
import com.example.percobaan.viewmodel.UserViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoriSiswa)
        }

        initializer {
            EntryViewModel(
                aplikasiSiswa().container.repositoriSiswa,
                aplikasiSiswa().container.repositoriMataKuliah // Perubahan
            )
        }


        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                aplikasiSiswa().container.repositoriSiswa
            )
        }

        initializer {
            EditViewModel(
                this.createSavedStateHandle(),
                aplikasiSiswa().container.repositoriSiswa
            )
        }
        initializer {
            UserViewModel(aplikasiSiswa().container.repositoriUser)
        }

        initializer {
            MataKuliahListViewModel(
                aplikasiSiswa().container.repositoriMataKuliah
            )
        }

        initializer {
            MataKuliahEntryViewModel(
                aplikasiSiswa().container.repositoriMataKuliah
            )
        }

        initializer {
            MataKuliahEditViewModel(
                aplikasiSiswa().container.repositoriMataKuliah
            )
        }


    }
}


fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            AplikasiSiswa)