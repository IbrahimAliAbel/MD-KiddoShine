package com.example.kiddoshine.api.response

import com.google.gson.annotations.SerializedName

data class KiddoResponse<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: T?
)
data class RegisterResponse(
    val code: Int,
    val status: String,
    val data: RegisterData
)

data class RegisterData(
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: String
)

data class LoginResponse(
    val code: Int,
    val status: String,
    val data: LoginData
)

data class LoginData(
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String,
    @SerializedName("userId") val userId: String
)