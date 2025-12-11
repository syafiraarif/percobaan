package com.example.percobaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ExitToApp // BARU
import androidx.compose.material.icons.automirrored.filled.List // BARU
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.room.Siswa
import com.example.percobaan.view.SiswaTopAppBar
import com.example.percobaan.view.route.DestinasiHome
import com.example.percobaan.viewmodel.HomeViewModel
import com.example.percobaan.viewmodel.provider.PenyediaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit, // BARU
    navigateToMatkulList: () -> Unit, // BARU
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                // BARU: Tombol Logout dan Mata Kuliah
                actions = {
                    // Tombol Mata Kuliah List
                    IconButton(onClick = navigateToMatkulList) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List, // UBAH IKON
                            contentDescription = stringResource(R.string.daftar_matakuliah)
                        )
                    }

                    // Tombol Logout
                    IconButton(onClick = navigateToLogin) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp, // UBAH IKON
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_large)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        }
    ) { innerPadding ->

        val uiStateSiswa by viewModel.homeUiState.collectAsState()

        BodyHome(
            itemSiswa = uiStateSiswa.listSiswa,
            onSiswaClick = { siswa -> navigateToItemUpdate(siswa.id) },
            searchQuery = viewModel.searchQuery, // Meneruskan state pencarian
            onSearchQueryChange = viewModel::updateSearchQuery, // Meneruskan fungsi update
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

// DEFINISI FUNGSI BODYHOME YANG DIKOREKSI
@Composable
fun BodyHome(
    itemSiswa: List<Siswa>,
    onSiswaClick: (Siswa) -> Unit,
    searchQuery: String, // Parameter baru ditambahkan
    onSearchQueryChange: (String) -> Unit, // Parameter baru ditambahkan
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Tambahkan kolom pencarian
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text(stringResource(R.string.search_hint)) }, // Menggunakan string resource baru
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .padding(top = dimensionResource(id = R.dimen.padding_small))
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))


        if (itemSiswa.isEmpty()) {
            Text(
                text = stringResource(R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListSiswa(
                itemSiswa = itemSiswa,
                onSiswaClick,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_small)
                )
            )
        }
    }
}

// ... (ListSiswa dan DataSiswa tetap sama)

@Composable
fun ListSiswa(
    itemSiswa: List<Siswa>,
    onSiswaClick: (Siswa) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = itemSiswa,
            key = { it.id }
        ) { person -> DataSiswa(
            siswa = person,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .clickable { onSiswaClick(person)}
        )
        }
    }
}

@Composable
fun DataSiswa(
    siswa: Siswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_small)
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null
                )

                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}