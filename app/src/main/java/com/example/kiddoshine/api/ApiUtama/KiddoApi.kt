package com.example.kiddoshine.api.ApiUtama

import android.content.Context
import com.example.kiddoshine.api.repository.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KiddoApi {
    private const val BASE_URL = "https://kiddoshine-api-v2-279022316915.asia-southeast1.run.app/"


    fun getApiService(context: Context): KiddoService {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val userPreferences = UserPreferences(context)

            val token = runBlocking {
                userPreferences.getAuthToken().first()
            }

            val request = if (token.isNullOrEmpty()) {

                chain.request()
            } else {

                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
            chain.proceed(request)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(KiddoService::class.java)
    }
}