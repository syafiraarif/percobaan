package com.example.percobaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.room.MataKuliah
import com.example.percobaan.view.route.DestinasiEntry
import com.example.percobaan.viewmodel.DetailSiswa
import com.example.percobaan.viewmodel.EntryViewModel
import com.example.percobaan.viewmodel.UIStateSiswa
import com.example.percobaan.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // BARU: Kumpulkan daftar Mata Kuliah
    val mataKuliahList by viewModel.mataKuliahList.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveSiswa()
                    navigateBack()
                }
            },
            listMataKuliah = mataKuliahList, // TERUSKAN
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    listMataKuliah: List<MataKuliah>, // BARU: Terima list Mata Kuliah
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_large)
        ),
        modifier = modifier.padding(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            listMataKuliah = listMataKuliah, // TERUSKAN
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswa(
    detailSiswa: DetailSiswa,
    modifier: Modifier = Modifier,
    onValueChange: (DetailSiswa) -> Unit = {},
    enabled: Boolean = true,
    listMataKuliah: List<MataKuliah> = emptyList() // BARU
) {
    val context = LocalContext.current
    val calendar = java.util.Calendar.getInstance()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        // Nama
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = { onValueChange(detailSiswa.copy(nama = it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Alamat
        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = { onValueChange(detailSiswa.copy(alamat = it)) },
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Telpon
        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = { onValueChange(detailSiswa.copy(telpon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.telpon)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown Kelas
        var dropdownExpanded by remember { mutableStateOf(false) }
        val opsiKelas = listOf("A", "B", "C", "D", "E")
        var textKelas by remember { mutableStateOf(detailSiswa.kelas) }

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                value = textKelas,
                onValueChange = {},
                readOnly = true,
                label = { Text("Kelas") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // penting untuk menu muncul di bawah TextField
            )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                opsiKelas.forEach { opsi ->
                    DropdownMenuItem(
                        text = { Text(opsi) },
                        onClick = {
                            textKelas = opsi
                            dropdownExpanded = false
                            onValueChange(detailSiswa.copy(kelas = opsi))
                        }
                    )
                }
            }
        }


        // Checkbox Peminatan
        val opsiPeminatan = listOf("RPL", "DKV", "TJKT")
        Text("Peminatan:")
        opsiPeminatan.forEach { item ->
            val checked = item in detailSiswa.peminatan
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        val updated = if (it) detailSiswa.peminatan + item else detailSiswa.peminatan - item
                        onValueChange(detailSiswa.copy(peminatan = updated))
                    }
                )
                Text(item)
            }
        }

        // BARU: Dropdown Mata Kuliah
        var dropdownMatkulExpanded by remember { mutableStateOf(false) }
        val selectedMatkul = listMataKuliah.find { it.id_matkul == detailSiswa.id_matkul }
        var textMatkul by remember(selectedMatkul) {
            mutableStateOf(selectedMatkul?.nama_matkul ?: "Pilih Mata Kuliah*")
        }

        ExposedDropdownMenuBox(
            expanded = dropdownMatkulExpanded,
            onExpandedChange = { dropdownMatkulExpanded = !dropdownMatkulExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = textMatkul,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.nama_matkul).replace("*", "")) }, // Label nama matkul
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownMatkulExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = dropdownMatkulExpanded,
                onDismissRequest = { dropdownMatkulExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                listMataKuliah.forEach { mk ->
                    DropdownMenuItem(
                        text = { Text(mk.nama_matkul) },
                        onClick = {
                            textMatkul = mk.nama_matkul
                            dropdownMatkulExpanded = false
                            onValueChange(detailSiswa.copy(id_matkul = mk.id_matkul))
                        }
                    )
                }
            }
        }

        // Tanggal Lahir pakai DatePickerDialog
        OutlinedTextField(
            value = detailSiswa.tanggal_lahir,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tanggal Lahir*") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    val dpd = android.app.DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val tgl = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                            onValueChange(detailSiswa.copy(tanggal_lahir = tgl))
                        },
                        calendar.get(java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH),
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)
                    )
                    dpd.show()
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                }
            }
        )

        // Required field
        if (enabled) {
            Text(
                text = stringResource(R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }

        // Divider
        Divider(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            thickness = dimensionResource(R.dimen.padding_small),
            color = Color.Blue
        )
    }
}