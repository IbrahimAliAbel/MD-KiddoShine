package com.example.kiddoshine.ui.artikel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArtikelViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Artikel Fragment"
    }
    val text: LiveData<String> = _text
}