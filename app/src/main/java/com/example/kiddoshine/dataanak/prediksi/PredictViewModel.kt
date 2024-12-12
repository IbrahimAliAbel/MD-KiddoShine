package com.example.kiddoshine.dataanak.prediksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiddoshine.api.Model.ModelApi
import com.example.kiddoshine.api.Model.PredictRequest
import kotlinx.coroutines.launch

class PredictViewModel : ViewModel() {
    private val _predictResult = MutableLiveData<PredictionResult>()
    val predictResult: LiveData<PredictionResult> = _predictResult

    fun makePrediction(
        userId: String,
        gender: String,
        age: Int,
        birthWeight: Double,
        birthLength: Double,
        bodyWeight: Double,
        bodyLength: Double,
        breastfeeding: Int
    ) {
        viewModelScope.launch {
            _predictResult.postValue(PredictionResult.Loading)

            try {
                val request = PredictRequest(
                    gender = gender,
                    age = age,
                    birthWeight = birthWeight,
                    birthLength = birthLength,
                    bodyWeight = bodyWeight,
                    bodyLength = bodyLength,
                    breastfeeding = breastfeeding
                )

                val response = ModelApi.getApiService().predict(userId, request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _predictResult.postValue(PredictionResult.Success(it))
                    } ?: run {
                        _predictResult.postValue(PredictionResult.Error("Data tidak tersedia."))
                    }
                } else {
                    _predictResult.postValue(PredictionResult.Error("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                _predictResult.postValue(PredictionResult.Error("Exception: ${e.message}"))
            }
        }
    }
}
