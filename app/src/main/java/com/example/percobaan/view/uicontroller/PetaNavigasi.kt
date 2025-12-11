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
import com.example.percobaan.view.HalamanDetailMataKuliah
import com.example.percobaan.view.HomeScreen
import com.example.percobaan.view.HalamanListMataKuliah
import com.example.percobaan.view.HalamanEntryMataKuliah
import com.example.percobaan.view.HalamanLogin
import com.example.percobaan.view.HalamanRegistrasi
import com.example.percobaan.view.route.DestinasiDetailSiswa
import com.example.percobaan.view.route.DestinasiDetailSiswa.itemIdArg
import com.example.percobaan.view.route.DestinasiEditSiswa
import com.example.percobaan.view.route.DestinasiEntry
import com.example.percobaan.view.route.DestinasiHome
import com.example.percobaan.view.route.DestinasiLogin
import com.example.percobaan.view.route.DestinasiRegister
import com.example.percobaan.view.route.DetailMataKuliah
import com.example.percobaan.view.route.EntryMataKuliah
import com.example.percobaan.view.route.ListMataKuliah


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
        startDestination = DestinasiLogin.route,
        modifier = Modifier
    ) {

        // ----------------------
        // 🔐 LOGIN SCREEN
        // ----------------------
        composable(DestinasiLogin.route) {
            HalamanLogin(
                navigateToRegister = {
                    navController.navigate(DestinasiRegister.route)
                },
                navigateToHome = {
                    // Pindah ke Home dan hapus layar Login dari back stack
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // ----------------------
        // 📝 REGISTER SCREEN
        // ----------------------
        composable(DestinasiRegister.route) {
            HalamanRegistrasi(
                navigateBack = { navController.popBackStack() },
                navigateToLogin = {
                    // Pindah ke Login dan hapus layar Register dari back stack
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiRegister.route) { inclusive = true }
                    }
                }
            )
        }

        // ----------------------
        // 🏠 HOME
        // ----------------------
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = { navController.navigate("${DestinasiDetailSiswa.route}/${it}") },
                navigateBack = { navController.popBackStack() },
                // Aksi Logout
                navigateToLogin = {
                    // Navigasi ke Login dan hapus semua history Home
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                },
                // Aksi ke List Mata Kuliah
                navigateToMatkulList = {
                    navController.navigate(ListMataKuliah.route)
                }
            )
        }

        // ----------------------
        // SISWA SCREENS
        // ----------------------
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

        // ----------------------
        // 📚 MATA KULIAH SCREENS
        // ----------------------
        composable(ListMataKuliah.route) {
            HalamanListMataKuliah(
                navigateToEntry = { navController.navigate(EntryMataKuliah.route) },
                navigateToUpdate = { navController.navigate("${DetailMataKuliah.route}/${it}") },
                navigateBack = { navController.navigateUp() } // Kembali ke Home
            )
        }

        composable(EntryMataKuliah.route) {
            HalamanEntryMataKuliah(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DetailMataKuliah.routeWithArgs,
            arguments = listOf(navArgument(DetailMataKuliah.argId) { type = NavType.IntType })
        ) {
            HalamanDetailMataKuliah(
                // Ganti dengan rute edit Mata Kuliah jika sudah dibuat
                navigateToEdit = { /* TODO: Navigasi ke Edit Mata Kuliah */ },
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}