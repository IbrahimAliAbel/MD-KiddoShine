package com.example.kiddoshine.api.Model

import com.google.gson.annotations.SerializedName

data class PredictResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PredictionData
)

data class PredictionData(
    @SerializedName("Stunting") val stunting: Int,
    @SerializedName("Details") val details: PredictionDetails
)

data class PredictionDetails(
    @SerializedName("Gender") val gender: Int,
    @SerializedName("Age") val age: Int,
    @SerializedName("Birth_Weight") val birthWeight: Double,
    @SerializedName("Birth_Length") val birthLength: Double,
    @SerializedName("Body_Weight") val bodyWeight: Double,
    @SerializedName("Body_Length") val bodyLength: Double,
    @SerializedName("Breastfeeding") val breastfeeding: Int
)