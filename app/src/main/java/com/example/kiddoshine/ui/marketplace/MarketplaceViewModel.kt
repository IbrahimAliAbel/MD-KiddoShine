package com.example.kiddoshine.ui.marketplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MarketplaceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Marketplace Fragment"
    }
    val text: LiveData<String> = _text
}