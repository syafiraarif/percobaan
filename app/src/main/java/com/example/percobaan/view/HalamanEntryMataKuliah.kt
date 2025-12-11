package com.example.percobaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.viewmodel.MataKuliahEntryViewModel
import com.example.percobaan.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntryMataKuliah(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(R.string.entry_matkul),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
        ) {
            // Form Input Nama Mata Kuliah
            OutlinedTextField(
                value = viewModel.uiState.nama_matkul,
                onValueChange = {
                    viewModel.updateState(viewModel.uiState.copy(nama_matkul = it))
                },
                label = { Text(stringResource(R.string.nama_matkul)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Memberikan sedikit ruang sebelum tombol
            Spacer(modifier = Modifier.height(16.dp))

            // Button Submit
            Button(
                onClick = {
                    // Simpan data dan kembali
                    viewModel.insert(onDone = navigateBack)
                },
                enabled = viewModel.uiState.isValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_submit))
            }
        }
    }
}