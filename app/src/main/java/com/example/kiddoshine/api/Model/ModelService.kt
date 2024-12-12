package com.example.kiddoshine.api.Model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ModelService {
    @POST("predict/{user_id}")
    suspend fun predict(
        @Path("user_id") userId: String,
        @Body request: PredictRequest
    ): Response<PredictResponse>
}