package com.example.percobaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val mataKuliahList by viewModel.mataKuliahList.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            EntrySiswaBody(
                uiStateSiswa = viewModel.uiStateSiswa,
                onSiswaValueChange = viewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveSiswa()
                        navigateBack()
                    }
                },
                listMataKuliah = mataKuliahList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}


@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    listMataKuliah: List<MataKuliah>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_large)
        ),
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            listMataKuliah = listMataKuliah,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
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
    listMataKuliah: List<MataKuliah> = emptyList()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // --- State Lokal untuk UI Dropdown ---
    var dropdownKelasExpanded by remember { mutableStateOf(false) }
    var dropdownMatkulExpanded by remember { mutableStateOf(false) }

    // --- Data Dropdown ---
    val opsiKelas = listOf("A", "B", "C", "D", "E")

    // FIX: Drived State untuk Mata Kuliah dan Kelas (Menggunakan nilai dari state ViewModel)
    // textKelas dan textMatkul akan otomatis update saat detailSiswa/listMataKuliah berubah.
    val selectedMatkul = listMataKuliah.find { it.id_matkul == detailSiswa.id_matkul }
    val textKelas = detailSiswa.kelas
    val textMatkul = selectedMatkul?.nama_matkul ?: "Pilih Mata Kuliah*"

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        // Nama, Alamat, Telpon (kode tetap)
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = { onValueChange(detailSiswa.copy(nama = it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = { onValueChange(detailSiswa.copy(alamat = it)) },
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = { onValueChange(detailSiswa.copy(telpon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.telpon)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        // Dropdown Kelas (FIXED)
        ExposedDropdownMenuBox(
            expanded = dropdownKelasExpanded,
            onExpandedChange = { dropdownKelasExpanded = !dropdownKelasExpanded }
        ) {
            OutlinedTextField(
                value = textKelas,
                onValueChange = {},
                readOnly = true,
                label = { Text("Kelas") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownKelasExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = dropdownKelasExpanded,
                onDismissRequest = { dropdownKelasExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                // FIX: Menampilkan opsi kelas
                opsiKelas.forEach { opsi ->
                    DropdownMenuItem(
                        text = { Text(opsi) },
                        onClick = {
                            dropdownKelasExpanded = false
                            onValueChange(detailSiswa.copy(kelas = opsi))
                        }
                    )
                }
            }
        }


        // Checkbox Peminatan (kode tetap)
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

        // Dropdown Mata Kuliah (FIXED)
        ExposedDropdownMenuBox(
            expanded = dropdownMatkulExpanded,
            onExpandedChange = { dropdownMatkulExpanded = !dropdownMatkulExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = textMatkul, // Menggunakan textMatkul yang diderivasi dari state
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.nama_matkul).replace("*", "")) },
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
                // FIX: Memastikan list Mata Kuliah ditampilkan
                listMataKuliah.forEach { mk ->
                    DropdownMenuItem(
                        text = { Text(mk.nama_matkul) },
                        onClick = {
                            dropdownMatkulExpanded = false
                            onValueChange(detailSiswa.copy(id_matkul = mk.id_matkul))
                        }
                    )
                }
            }
        }


        // Tanggal Lahir pakai DatePickerDialog (FIXED)
        OutlinedTextField(
            value = detailSiswa.tanggal_lahir,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tanggal Lahir*") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    // FIX: Logika DatePicker yang benar
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    val dpd = android.app.DatePickerDialog(
                        context,
                        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                            val tgl = String.format("%02d/%02d/%04d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
                            onValueChange(detailSiswa.copy(tanggal_lahir = tgl))
                        },
                        year,
                        month,
                        day
                    )
                    dpd.show() // Menampilkan dialog
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                }
            }
        )

        // Required field (tetap)
        if (enabled) {
            Text(
                text = stringResource(R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }

        // Divider (tetap)
        HorizontalDivider(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            thickness = dimensionResource(id = R.dimen.padding_small),
            color = Color.Blue
        )
    }
}