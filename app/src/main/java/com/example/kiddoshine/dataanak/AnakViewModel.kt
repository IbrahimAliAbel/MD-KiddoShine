package com.example.kiddoshine.dataanak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kiddoshine.database.Anak
import com.example.kiddoshine.database.AnakDatabase
import com.example.kiddoshine.repository.AnakRepository
import kotlinx.coroutines.launch

class AnakViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnakRepository
    val allAnak: LiveData<List<Anak>>

    init {
        val anakDao = AnakDatabase.getDatabase(application).anakDao()
        repository = AnakRepository(anakDao)
        allAnak = repository.allAnak
    }

    fun insert(anak: Anak) = viewModelScope.launch {
        repository.insert(anak)
    }

    fun update(anak: Anak) = viewModelScope.launch {
        repository.update(anak)  // Update data anak
    }

    fun deleteAnak(anak: Anak) = viewModelScope.launch {
        repository.delete(anak)
    }

    fun getAnakById(id: Int): LiveData<Anak?> {
        return repository.getAnakById(id)
    }

    fun getLatestAnakId(): LiveData<Int> {
        return repository.getLatestAnakId()
    }
}