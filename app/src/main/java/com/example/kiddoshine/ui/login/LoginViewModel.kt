package com.example.kiddoshine.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiddoshine.api.ApiUtama.KiddoApi
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.api.response.KiddoResponse
import com.example.kiddoshine.api.response.LoginData
import com.example.kiddoshine.api.response.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val userPreference: UserPreferences, private val context: Context) : ViewModel() {
    private val _loginStatus = MutableStateFlow<LoginStatus>(LoginStatus.Idle)
    val loginStatus: StateFlow<LoginStatus> = _loginStatus

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginStatus.value = LoginStatus.Error("Email dan Password tidak boleh kosong!")
            return
        }

        viewModelScope.launch {
            val userRequest = UserRequest(name = "", email = email, password = password)
            _loginStatus.value = LoginStatus.Loading
            try {
                val response: Response<KiddoResponse<LoginData>> = KiddoApi.getApiService(context).loginUser(userRequest)

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.code == 200) {
                        loginResponse.data?.let { data ->
                            val userId = data.userId
                            val token = data.token
                            val message = data.message

                            userPreference.saveUserId(userId)
                            userPreference.saveAuthToken(token)
                            userPreference.saveLoginStatus(true)
                            _loginStatus.value = LoginStatus.Success(message)
                        } ?: run {
                            _loginStatus.value = LoginStatus.Error("Data login tidak lengkap!")
                        }
                    } else {
                        _loginStatus.value = LoginStatus.Error("Login gagal. Periksa email dan password!")
                    }
                } else {
                    _loginStatus.value = LoginStatus.Error("Login gagal. Periksa email dan password!")
                }
            } catch (e: Exception) {
                _loginStatus.value = LoginStatus.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }
}

sealed class LoginStatus {
    object Idle : LoginStatus()
    object Loading : LoginStatus()
    data class Success(val message: String) : LoginStatus()
    data class Error(val errorMessage: String) : LoginStatus()
}