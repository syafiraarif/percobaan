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
import com.example.percobaan.view.HalamanListMataKuliah
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
import com.example.percobaan.view.HalamanLogin
import com.example.percobaan.view.HalamanRegistrasi
import com.example.percobaan.view.HalamanEntryMataKuliah // BARU

@Composable
fun SiswaApp(navController: NavHostController= rememberNavController(), modifier: Modifier){
    com.example.percobaan.view.uicontroller.HostNavigasi(navController = navController)
}
// ... (Pastikan import berikut ada di bagian atas file)
// import com.example.percobaan.view.HalamanLogin
// import com.example.percobaan.view.HalamanRegistrasi

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
            HalamanLogin( // Menggunakan fungsi HalamanLogin yang sebenarnya
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
            HalamanRegistrasi( // Menggunakan fungsi HalamanRegistrasi yang sebenarnya
                navigateBack = { navController.popBackStack() }, // TAMBAHKAN INI
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
                // BARU: Aksi Logout
                navigateToLogin = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                },
                // BARU: Aksi ke List Mata Kuliah
                navigateToMatkulList = {
                    navController.navigate(ListMataKuliah.route)
                }
            )
        }

        // ... (Destinasi Entry, Detail, dan Edit Siswa tetap sama)
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
        // 📚 MATA KULIAH SCREENS (BARU)
        // ----------------------
        composable(ListMataKuliah.route) {
            HalamanListMataKuliah(
                navigateToEntry = { navController.navigate(EntryMataKuliah.route) },
                navigateToUpdate = { navController.navigate("${DetailMataKuliah.route}/${it}") },
                navigateBack = { navController.navigateUp() } // Kembali ke Home
            )
        }

        composable(EntryMataKuliah.route) {
            HalamanEntryMataKuliah( // IMPLEMENTASI BARU
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = DetailMataKuliah.routeWithArgs,
            arguments = listOf(navArgument(DetailMataKuliah.argId) { type = NavType.IntType })
        ) {
            // Tempat untuk HalamanDetailMataKuliah
        }
    }
}