package com.example.kiddoshine.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface ApiService {
    @POST("/api/prediksi-stunting")
    fun prediksiStunting(@Body data: StuntingData): Call<PrediksiResponse>
}