package com.example.kiddoshine.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiddoshine.api.repository.UserPreferences
import kotlinx.coroutines.launch

class SettingsViewModel (private val userPreferences: UserPreferences) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }
}