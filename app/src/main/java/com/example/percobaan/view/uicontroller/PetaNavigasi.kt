package com.example.percobaan.view.uicontroller

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.percobaan.view.DetailSiswaScreen
import com.example.percobaan.view.EditSiswaScreen
import com.example.percobaan.view.EntrySiswaScreen
import com.example.percobaan.view.HomeScreen
import com.example.percobaan.view.route.DestinasiDetailSiswa
import com.example.percobaan.view.route.DestinasiDetailSiswa.itemIdArg
import com.example.percobaan.view.route.DestinasiEditSiswa
import com.example.percobaan.view.route.DestinasiEntry
import com.example.percobaan.view.route.DestinasiHome
import com.example.percobaan.view.route.DestinasiLogin
import com.example.percobaan.view.route.DestinasiRegister


@Composable
fun SiswaApp(navController: NavHostController= rememberNavController(), modifier: Modifier){
    com.example.percobaan.view.uicontroller.HostNavigasi(navController = navController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route, // Login jadi halaman awal
        modifier = Modifier
    ) {

        // ----------------------
        // 🔐 LOGIN SCREEN
        // ----------------------
        composable(DestinasiLogin.route) {
            LoginScreen(
                navigateToRegister = { navController.navigate(DestinasiRegister.route) },
                navigateToHome = { navController.navigate(DestinasiHome.route) }
            )
        }

        // ----------------------
        // 📝 REGISTER SCREEN
        // ----------------------
        composable(DestinasiRegister.route) {
            RegisterScreen(
                navigateToLogin = { navController.navigate(DestinasiLogin.route) }
            )
        }

        // ----------------------
        // 🏠 HOME (sudah ada)
        // ----------------------
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = { navController.navigate("${DestinasiDetailSiswa.route}/${it}") },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = { navController.popBackStack() })
        }

        composable(
            route = DestinasiDetailSiswa.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailSiswa.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailSiswaScreen(
                navigateToEditItem = { navController.navigate("${DestinasiEditSiswa.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = DestinasiEditSiswa.routeWithArgs,
            arguments = listOf(
                navArgument(itemIdArg) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            EditSiswaScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit
) {
    // isi UI register di sini
}


@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit
) {
    // isi UI login di sini
}
