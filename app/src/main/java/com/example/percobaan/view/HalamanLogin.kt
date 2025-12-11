package com.example.percobaan.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.percobaan.R
import com.example.percobaan.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanLogin(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    val uiState = viewModel.uiStateUser
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(R.string.login_title),
                canNavigateBack = false
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

            OutlinedTextField(
                value = uiState.username,
                onValueChange = {
                    viewModel.updateUserState(uiState.copy(username = it))
                },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = {
                    viewModel.updateUserState(uiState.copy(password = it))
                },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    viewModel.loginUser { success, message ->

                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }

                        if (success) navigateToHome()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_login))
            }

            TextButton(
                onClick = navigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Belum punya akun? Registrasi")
            }
        }
    }
}
