package com.example.percobaan.repositori

import android.app.Application
import android.content.Context
import com.example.percobaan.room.DatabaseSiswa

// Interface container
interface ContainerApp {
    val repositoriSiswa: RepositoriSiswa
    val repositoriUser: RepositoriUser   // tambahan
}

// Implementasi container
class ContainerDataApp(private val context: Context) : ContainerApp {

    private val database by lazy {
        DatabaseSiswa.getDatabase(context)
    }

    override val repositoriSiswa: RepositoriSiswa by lazy {
        OfflineRepositoriSiswa(database.SiswaDao())
    }

    override val repositoriUser: RepositoriUser by lazy {
        OfflineRepositoriUser(database.UserDao())
    }
}

// Application class
class AplikasiSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}
