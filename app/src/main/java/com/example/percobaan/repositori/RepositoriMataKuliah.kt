package com.example.percobaan.repositori

import com.example.percobaan.room.MataKuliah
import com.example.percobaan.room.MataKuliahDao
import kotlinx.coroutines.flow.Flow

// Interface repositori
interface RepositoriMataKuliah {
    fun getAllMataKuliahStream(): Flow<List<MataKuliah>>
    fun getMataKuliah(id: Int): Flow<MataKuliah>

    suspend fun insert(mataKuliah: MataKuliah)
    suspend fun update(mataKuliah: MataKuliah)
    suspend fun delete(mataKuliah: MataKuliah)
}

// Implementasi offline (pakai Room)
class OfflineRepositoriMataKuliah(
    private val mataKuliahDao: MataKuliahDao
) : RepositoriMataKuliah {

    override fun getAllMataKuliahStream(): Flow<List<MataKuliah>> =
        mataKuliahDao.getAllMatkul()

    override fun getMataKuliah(id: Int): Flow<MataKuliah> =
        mataKuliahDao.getMataKuliah(id)

    override suspend fun insert(mataKuliah: MataKuliah) =
        mataKuliahDao.insert(mataKuliah)

    override suspend fun update(mataKuliah: MataKuliah) =
        mataKuliahDao.update(mataKuliah)

    override suspend fun delete(mataKuliah: MataKuliah) =
        mataKuliahDao.delete(mataKuliah)
}
