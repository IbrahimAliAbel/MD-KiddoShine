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

            e.printStackTrace()
        }
    }

    suspend fun update(anak: Anak) {
        try {
            anakDao.update(anak)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun delete(anak: Anak) {
        try {
            anakDao.delete(anak)
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    fun getAnakById(id: Int): LiveData<Anak?> {
        return anakDao.getAnakById(id)
    }

    fun getLatestAnakId(): LiveData<Int> {
        return anakDao.getLatestAnakId()
    }


    suspend fun deleteById(id: Int) {
        try {
            anakDao.deleteById(id)
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }
}