package com.example.kiddoshine.api.Model

import com.google.gson.annotations.SerializedName

data class PredictRequest(
    @SerializedName("Gender") val gender: String,
    @SerializedName("Age") val age: Int,
    @SerializedName("Birth_Weight") val birthWeight: Double,
    @SerializedName("Birth_Length") val birthLength: Double,
    @SerializedName("Body_Weight") val bodyWeight: Double,
    @SerializedName("Body_Length") val bodyLength: Double,
    @SerializedName("Breastfeeding") val breastfeeding: Int
)
