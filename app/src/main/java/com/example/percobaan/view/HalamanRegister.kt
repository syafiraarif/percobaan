package com.example.percobaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation // Import untuk menyembunyikan password
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.viewmodel.UserViewModel
import com.example.percobaan.viewmodel.provider.PenyediaViewModel // Pastikan ini diimport
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanRegistrasi(
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: UserViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiStateUser
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var checked by remember { mutableStateOf(false) }

    // Fix: Ekstrak string resource di context Composable
    val errorSyaratText = stringResource(R.string.error_syarat)
    val btnRegisterText = stringResource(R.string.btn_register)

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(R.string.register_title),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Kolom Username (Diambil dari UserViewModel.kt)
            OutlinedTextField(
                value = uiState.username,
                onValueChange = {
                    viewModel.updateUserState(uiState.copy(username = it))
                },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Kolom Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = {
                    viewModel.updateUserState(uiState.copy(email = it))
                },
                label = { Text(stringResource(R.string.email)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Kolom Password
            OutlinedTextField(
                value = uiState.password,
                onValueChange = {
                    viewModel.updateUserState(uiState.copy(password = it))
                },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(), // Menyembunyikan teks password
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Checkbox Syarat & Ketentuan
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
                Text(
                    text = stringResource(R.string.syarat_ketentuan),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Tombol Register
            Button(
                onClick = {
                    if (!checked) {
                        scope.launch {
                            // Menggunakan variabel non-Composable
                            snackbarHostState.showSnackbar(errorSyaratText)
                        }
                        return@Button
                    }

                    // Validasi minimal field terisi
                    if (uiState.username.isBlank() || uiState.email.isBlank() || uiState.password.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Semua field wajib diisi")
                        }
                        return@Button
                    }

                    viewModel.registerUser { success, message ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }

                        // Pindah ke halaman Login setelah berhasil
                        if (success) navigateToLogin()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(btnRegisterText)
            }
        }
    }
}