package com.example.kiddoshine.ui.ListAnak

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiddoshine.database.Anak

//data class Anak(val nama: String, val tinggiBadan: String, val beratBadan: String)

class ListAnakViewModel : ViewModel() {

    // LiveData untuk menyimpan data anak
    private val _anakData = MutableLiveData<Anak?>()
    val anakData: LiveData<Anak?> get() = _anakData

    // Metode untuk mengatur data anak
    fun setAnakData(anak: Anak) {
        _anakData.value = anak
    }
}