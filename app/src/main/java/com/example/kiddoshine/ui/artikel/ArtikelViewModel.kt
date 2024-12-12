package com.example.kiddoshine.ui.artikel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiddoshine.api.ApiArtikel.ApiConfig
import com.example.kiddoshine.api.response.ResponseArtikel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.kiddoshine.api.response.ArticlesItem


class ArtikelViewModel : ViewModel() {


    private val _articles = MutableLiveData<List<ArticlesItem>>()
    val articles: LiveData<List<ArticlesItem>> get() = _articles


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun fetchArticles() {
        _loading.value = true

        ApiConfig.apiService.getHealthArticles().enqueue(object : Callback<ResponseArtikel> {
            override fun onResponse(call: Call<ResponseArtikel>, response: Response<ResponseArtikel>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _articles.value = response.body()?.articles ?: emptyList()
                } else {
                    _error.value = "Gagal memuat artikel"
                    Log.e("ArtikelViewModel", "Gagal memuat artikel")
                }
            }

            override fun onFailure(call: Call<ResponseArtikel>, t: Throwable) {
                _loading.value = false
                _error.value = "Error: ${t.message}"
                Log.e("ArtikelViewModel", "Error: ${t.message}")
            }
        })
    }
}