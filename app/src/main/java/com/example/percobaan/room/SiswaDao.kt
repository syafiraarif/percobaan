package com.example.percobaan.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SiswaDao {
    @Query("SELECT * FROM tblSiswa WHERE nama LIKE :searchQuery or alamat LIKE :searchQuery ORDER BY nama ASC")
    fun getAllSiswa(searchQuery: String): Flow<List<Siswa>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(siswa: Siswa)

    @Query("SELECT * from tblSiswa WHERE id = :id")
    fun getSiswa(id: Int): Flow<Siswa?>

    @Delete
    suspend fun delete(siswa: Siswa)

    @Update
    suspend fun update (siswa: Siswa)

}