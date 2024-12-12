package com.example.kiddoshine.ui.marketplace

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MarketplaceViewModel(application: Application) : AndroidViewModel(application) {

    private val _productList = MutableLiveData<List<produk>>()
    val productList: LiveData<List<produk>> get() = _productList

    fun setProductData(productData: List<produk>) {
        _productList.value = productData
    }
}