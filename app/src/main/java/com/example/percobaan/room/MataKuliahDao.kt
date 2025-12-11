package com.example.percobaan.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {

    @Query("SELECT * FROM tblMataKuliah ORDER BY nama_matkul ASC")
    fun getAllMatkul(): Flow<List<MataKuliah>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mataKuliah: MataKuliah)

    @Query("SELECT * FROM tblMataKuliah WHERE id_matkul = :id")
    fun getMataKuliah(id: Int): Flow<MataKuliah>

    @Delete
    suspend fun delete(mataKuliah: MataKuliah)

    @Update
    suspend fun update(mataKuliah: MataKuliah)
}