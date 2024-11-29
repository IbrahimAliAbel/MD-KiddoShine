package com.example.kiddoshine.repository

import androidx.lifecycle.LiveData
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.database.AnakDao

class AnakRepository(private val anakDao: AnakDao) {
    val allAnak: LiveData<List<Anak>> = anakDao.getAllAnak()

    suspend fun insert(anak: Anak) {
        try {
            anakDao.insert(anak)
        } catch (e: Exception) {
            // Penanganan error jika diperlukan
            e.printStackTrace()
        }
    }

    suspend fun update(anak: Anak) {
        try {
            anakDao.update(anak)  // Memanggil fungsi update dari DAO
        } catch (e: Exception) {
            // Penanganan error jika diperlukan
            e.printStackTrace()
        }
    }

    suspend fun delete(anak: Anak) {
        try {
            anakDao.delete(anak)  // Memanggil fungsi delete dari DAO
        } catch (e: Exception) {
            // Penanganan error jika diperlukan
            e.printStackTrace()
        }
    }

    fun getAnakById(id: Int): LiveData<Anak?> {
        return anakDao.getAnakById(id)
    }

    // Fungsi tambahan untuk menghapus berdasarkan ID
    suspend fun deleteById(id: Int) {
        try {
            anakDao.deleteById(id)
        } catch (e: Exception) {
            // Penanganan error jika diperlukan
            e.printStackTrace()
        }
    }
}