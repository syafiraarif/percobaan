package com.example.percobaan.repositori

import com.example.percobaan.room.Siswa
import com.example.percobaan.room.SiswaDao
import kotlinx.coroutines.flow.Flow

interface RepositoriSiswa {
    fun getAllSiswaStream(searchQuery: String): Flow<List<Siswa>> // Disesuaikan
    suspend fun insertSiswa(siswa: Siswa)

    fun getSiswaStream(id: Int):   Flow<Siswa?>
    suspend fun deleteSiswa(siswa: Siswa)
    suspend fun updateSiswa(siswa: Siswa)
}

class OfflineRepositoriSiswa(
    private val siswaDao: SiswaDao
): RepositoriSiswa {
    override fun getAllSiswaStream(searchQuery: String): Flow<List<Siswa>> = siswaDao.getAllSiswa(searchQuery)
    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)

    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)
    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)
    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)

}