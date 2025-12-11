package com.example.percobaan.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.room.MataKuliah
import com.example.percobaan.viewmodel.provider.PenyediaViewModel
import com.example.percobaan.view.route.DestinasiNavigasi
import com.example.percobaan.viewmodel.MataKuliahListViewModel
import com.example.percobaan.viewmodel.MataKuliahListUiState // BARU

/* ROUTE */
object DestinasiListMataKuliah : DestinasiNavigasi {
    override val route = "list_matakuliah"
    override val titleRes = R.string.daftar_matakuliah
}

/* SCREEN */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanListMataKuliah(
    navigateToEntry: () -> Unit,
    navigateToUpdate: (Int) -> Unit,
    navigateBack: () -> Unit, // BARU
    modifier: Modifier = Modifier,
    viewModel: MataKuliahListViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiListMataKuliah.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack, // UBAH
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_matkul) // UBAH
                )
            }
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        BodyListMatkul(
            listMatkul = uiState.listMatkul,
            onClickMatkul = { navigateToUpdate(it.id_matkul) },
            searchQuery = viewModel.searchQuery, // BARU
            onSearchQueryChange = viewModel::updateSearchQuery, // BARU
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

/* BODY */
@Composable
fun BodyListMatkul(
    listMatkul: List<MataKuliah>,
    onClickMatkul: (MataKuliah) -> Unit,
    searchQuery: String, // BARU
    onSearchQueryChange: (String) -> Unit, // BARU
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text(stringResource(R.string.search_matkul)) }, // UBAH
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        if (listMatkul.isEmpty()) {
            Text(
                text = stringResource(R.string.no_matkul), // UBAH
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ListMatkul(
                listMatkul = listMatkul,
                onClickMatkul = onClickMatkul
            )
        }
    }
}

/* LIST */
@Composable
fun ListMatkul(
    listMatkul: List<MataKuliah>,
    onClickMatkul: (MataKuliah) -> Unit
) {
    LazyColumn {
        items(
            items = listMatkul,
            key = { it.id_matkul }
        ) { matkul ->
            ItemMatkul(
                mataKuliah = matkul,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onClickMatkul(matkul) }
            )
        }
    }
}

/* ITEM CARD */
@Composable
fun ItemMatkul(
    mataKuliah: MataKuliah,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = mataKuliah.nama_matkul,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}