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
import com.example.percobaan.view.*

import com.example.percobaan.view.route.*

@Composable
fun SiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = modifier
    ) {

        // ------------------------------------------------
        // 🔐 LOGIN
        // ------------------------------------------------
        composable(DestinasiLogin.route) {
            HalamanLogin(
                navigateToRegister = {
                    navController.navigate(DestinasiRegister.route)
                },
                navigateToHome = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // ------------------------------------------------
        // 📝 REGISTER
        // ------------------------------------------------
        composable(DestinasiRegister.route) {
            HalamanRegistrasi(
                navigateBack = { navController.popBackStack() },
                navigateToLogin = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiRegister.route) { inclusive = true }
                    }
                }
            )
        }

        // ------------------------------------------------
        // 🏠 HOME
        // ------------------------------------------------
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = { navController.navigate("${DestinasiDetailSiswa.route}/$it") },
                navigateBack = { navController.popBackStack() },
                navigateToLogin = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                },
                navigateToMatkulList = {
                    navController.navigate(ListMataKuliah.route)
                }
            )
        }

        // ------------------------------------------------
        // 📌 SISWA ROUTES
        // ------------------------------------------------

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
                navArgument(DestinasiEditSiswa.itemIdArg) {
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

        // ------------------------------------------------
        // 📚 MATA KULIAH ROUTES (FIX + LENGKAP)
        // ------------------------------------------------

        // LIST
        composable(ListMataKuliah.route) {
            HalamanListMataKuliah(
                navigateToEntry = { navController.navigate(EntryMataKuliah.route) },
                navigateToUpdate = { navController.navigate("${DetailMataKuliah.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        // ENTRY
        composable(EntryMataKuliah.route) {
            HalamanEntryMataKuliah(
                navigateBack = { navController.popBackStack() }
            )
        }

        // DETAIL
        composable(
            route = DetailMataKuliah.routeWithArgs,
            arguments = listOf(
                navArgument(DetailMataKuliah.argId) { type = NavType.IntType }
            )
        ) {
            HalamanDetailMataKuliah(
                navigateToEdit = { id -> navController.navigate("${EditMataKuliah.route}/$id") },
                navigateBack = { navController.navigateUp() }
            )
        }
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
                import com.example.percobaan.view.*

                import com.example.percobaan.view.route.*

                @Composable
                fun SiswaApp(
                    navController: NavHostController = rememberNavController(),
                    modifier: Modifier = Modifier
                ) {
                    HostNavigasi(navController = navController)
                }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun HostNavigasi(
            navController: NavHostController,
            modifier: Modifier = Modifier
        ) {
            NavHost(
                navController = navController,
                startDestination = DestinasiLogin.route,
                modifier = modifier
            ) {

                // ------------------------------------------------
                // 🔐 LOGIN
                // ------------------------------------------------
                composable(DestinasiLogin.route) {
                    HalamanLogin(
                        navigateToRegister = {
                            navController.navigate(DestinasiRegister.route)
                        },
                        navigateToHome = {
                            navController.navigate(DestinasiHome.route) {
                                popUpTo(DestinasiLogin.route) { inclusive = true }
                            }
                        }
                    )
                }

                // ------------------------------------------------
                // 📝 REGISTER
                // ------------------------------------------------
                composable(DestinasiRegister.route) {
                    HalamanRegistrasi(
                        navigateBack = { navController.popBackStack() },
                        navigateToLogin = {
                            navController.navigate(DestinasiLogin.route) {
                                popUpTo(DestinasiRegister.route) { inclusive = true }
                            }
                        }
                    )
                }

                // ------------------------------------------------
                // 🏠 HOME
                // ------------------------------------------------
                composable(DestinasiHome.route) {
                    HomeScreen(
                        navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                        navigateToItemUpdate = { navController.navigate("${DestinasiDetailSiswa.route}/$it") },
                        navigateBack = { navController.popBackStack() },
                        navigateToLogin = {
                            navController.navigate(DestinasiLogin.route) {
                                popUpTo(DestinasiHome.route) { inclusive = true }
                            }
                        },
                        navigateToMatkulList = {
                            navController.navigate(ListMataKuliah.route)
                        }
                    )
                }

                // ------------------------------------------------
                // 📌 SISWA ROUTES
                // ------------------------------------------------

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
                        navArgument(DestinasiEditSiswa.itemIdArg) {
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

                // ------------------------------------------------
                // 📚 MATA KULIAH ROUTES (FIX + LENGKAP)
                // ------------------------------------------------

                // LIST
                composable(ListMataKuliah.route) {
                    HalamanListMataKuliah(
                        navigateToEntry = { navController.navigate(EntryMataKuliah.route) },
                        navigateToUpdate = { navController.navigate("${DetailMataKuliah.route}/$it") },
                        navigateBack = { navController.navigateUp() }
                    )
                }

                // ENTRY
                composable(EntryMataKuliah.route) {
                    HalamanEntryMataKuliah(
                        navigateBack = { navController.popBackStack() }
                    )
                }

                // DETAIL
                composable(
                    route = DetailMataKuliah.routeWithArgs,
                    arguments = listOf(
                        navArgument(DetailMataKuliah.argId) { type = NavType.IntType }
                    )
                ) {
                    HalamanDetailMataKuliah(
                        navigateToEdit = { id -> navController.navigate("${EditMataKuliah.route}/$id") },
                        navigateBack = { navController.navigateUp() }
                    )
                }

                // EDIT (BARU DIBUAT SUPAYA LENGKAP)
                composable(
                    route = EditMataKuliah.routeWithArgs,
                    arguments = listOf(
                        navArgument(EditMataKuliah.argId) { type = NavType.IntType }
                    )
                ) {
                    HalamanEditMataKuliah(
                        navigateBack = { navController.popBackStack() }
                    )
                }
            }
        }

    }
}
