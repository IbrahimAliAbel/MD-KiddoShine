package com.example.kiddoshine.ui.ListAnak

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiddoshine.database.Anak


class ListAnakViewModel : ViewModel() {

    private val _anakData = MutableLiveData<Anak?>()
    val anakData: LiveData<Anak?> get() = _anakData

    fun setAnakData(anak: Anak) {
        _anakData.value = anak
    }
}