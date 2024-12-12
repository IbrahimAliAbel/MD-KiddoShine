package com.example.kiddoshine.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiddoshine.api.ApiUtama.KiddoApi
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.api.response.KiddoResponse
import com.example.kiddoshine.api.response.RegisterData
import com.example.kiddoshine.api.response.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val context: Context, private val userPreferences: UserPreferences) : ViewModel() {

    private val _registerStatus = MutableStateFlow<RegisterStatus>(RegisterStatus.Idle)
    val registerStatus: StateFlow<RegisterStatus> = _registerStatus

    fun registerUser(nama: String, email: String, password: String) {

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _registerStatus.value = RegisterStatus.Error("Semua field harus diisi!")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerStatus.value = RegisterStatus.Error("Email tidak valid!")
            return
        }

        if (password.length < 6) {
            _registerStatus.value = RegisterStatus.Error("Password minimal 6 karakter!")
            return
        }

        viewModelScope.launch {
            val userRequest = UserRequest(nama, email, password)
            _registerStatus.value = RegisterStatus.Loading
            try {

                val response: Response<KiddoResponse<RegisterData>> = KiddoApi.getApiService(context).registerUser(userRequest)

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null && registerResponse.code == 201) {

                        registerResponse.data?.let { data ->
                            val message = data.message
                            val id = data.id

                            userPreferences.saveAuthToken(id)

                            _registerStatus.value = RegisterStatus.Success(message)
                        } ?: run {
                            _registerStatus.value = RegisterStatus.Error("Data pendaftaran tidak lengkap!")
                        }
                    } else {
                        _registerStatus.value = RegisterStatus.Error("Pendaftaran gagal: ${registerResponse?.status ?: "Unknown error"}")
                    }
                } else {
                    _registerStatus.value = RegisterStatus.Error("Pendaftaran gagal! Kode error: ${response.code()}")
                }
            } catch (e: Exception) {
                _registerStatus.value = RegisterStatus.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }
}

sealed class RegisterStatus {
    object Idle : RegisterStatus()
    object Loading : RegisterStatus()
    data class Success(val message: String) : RegisterStatus()
    data class Error(val errorMessage: String) : RegisterStatus()
}