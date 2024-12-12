package com.example.kiddoshine.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.ui.login.LoginViewModel
import com.example.kiddoshine.ui.settings.SettingsViewModel
import com.example.kiddoshine.ui.register.RegisterViewModel

class ViewModelFactory(
    private val userPreferences: UserPreferences,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userPreferences, context) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(context, userPreferences) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(userPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


