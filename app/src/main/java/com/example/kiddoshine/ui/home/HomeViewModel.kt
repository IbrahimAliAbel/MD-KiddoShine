package com.example.kiddoshine.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _ovalData = MutableLiveData<List<String>>()
    val ovalData: LiveData<List<String>> = _ovalData

    init {

        loadOvalData()
    }

    private fun loadOvalData() {
        _ovalData.value = listOf("Oval 1", "Oval 2", "Oval 3", "Oval 4")
    }
}
