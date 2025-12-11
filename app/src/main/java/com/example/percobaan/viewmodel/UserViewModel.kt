package com.example.percobaan.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.percobaan.repositori.RepositoriUser
import com.example.percobaan.room.User
import kotlinx.coroutines.launch

class UserViewModel(
    private val repositoriUser: RepositoriUser
) : ViewModel() {

    var uiStateUser by mutableStateOf(UserUiState())
        private set

    fun updateUserState(newState: UserUiState) {
        uiStateUser = newState
    }

    // REGISTER
    fun registerUser(onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {

            // username wajib
            if (uiStateUser.username.isBlank() ||
                uiStateUser.email.isBlank() ||
                uiStateUser.password.isBlank()
            ) {
                onResult(false, "Semua field wajib diisi")
                return@launch
            }

            val exists = repositoriUser.checkUsername(uiStateUser.username)
            if (exists > 0) {
                onResult(false, "Username sudah digunakan")
            } else {
                val user = User(
                    username = uiStateUser.username,
                    email = uiStateUser.email,
                    password = uiStateUser.password
                )
                repositoriUser.register(user)
                onResult(true, "Registrasi berhasil")
            }
        }
    }

    // LOGIN
    fun loginUser(onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {

            if (uiStateUser.username.isBlank() || uiStateUser.password.isBlank()) {
                onResult(false, "Username dan password wajib diisi")
                return@launch
            }

            val user = repositoriUser.login(
                uiStateUser.username,
                uiStateUser.password
            )

            if (user != null) {
                onResult(true, "Login berhasil")
            } else {
                onResult(false, "Username atau password salah")
            }
        }
    }
}

data class UserUiState(
    val username: String = "",
    val email: String = "",
    val password: String = ""
)
