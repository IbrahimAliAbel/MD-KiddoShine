package com.example.kiddoshine.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnakDao {

    @Insert
    suspend fun insert(anak: Anak) // Fungsi untuk menyisipkan entitas Anak ke dalam database

    @Update
    suspend fun update(anak: Anak) // Fungsi untuk mengupdate entitas Anak

    @Delete
    suspend fun delete(anak: Anak) // Fungsi untuk menghapus entitas Anak

    @Query("DELETE FROM anak_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM anak_table WHERE id = :id")
    fun getAnakById(id: Int): LiveData<Anak?>

    @Query("SELECT * FROM anak_table")
    fun getAllAnak(): LiveData<List<Anak>> // Fungsi untuk mengambil semua entitas Anak
}