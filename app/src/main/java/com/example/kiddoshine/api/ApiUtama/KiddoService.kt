package com.example.kiddoshine.api.ApiUtama


import com.example.kiddoshine.api.response.KiddoResponse
import com.example.kiddoshine.api.response.LoginData
import com.example.kiddoshine.api.response.RegisterData
import com.example.kiddoshine.api.response.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KiddoService {
    @POST("register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<KiddoResponse<RegisterData>>

    @POST("login")
    suspend fun loginUser(@Body userRequest: UserRequest): Response<KiddoResponse<LoginData>>

}