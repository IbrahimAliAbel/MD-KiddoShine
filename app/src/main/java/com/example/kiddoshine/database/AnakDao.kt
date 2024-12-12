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
    suspend fun insert(anak: Anak)

    @Update
    suspend fun update(anak: Anak)

    @Delete
    suspend fun delete(anak: Anak)

    @Query("DELETE FROM anak_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM anak_table WHERE id = :id")
    fun getAnakById(id: Int): LiveData<Anak?>

    @Query("SELECT * FROM anak_table")
    fun getAllAnak(): LiveData<List<Anak>> 

    @Query("SELECT id FROM anak_table ORDER BY id DESC LIMIT 1")
    fun getLatestAnakId(): LiveData<Int>
}