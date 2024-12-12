package com.example.kiddoshine.dataanak.prediksi

import com.example.kiddoshine.api.Model.PredictResponse

sealed class PredictionResult {
    object Idle : PredictionResult()
    object Loading : PredictionResult()
    data class Success(val response: PredictResponse) : PredictionResult()
    data class Error(val errorMessage: String) : PredictionResult()
}