package com.example.kiddoshine.api.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(context: Context) {

    private val dataStore = context.userPreferences

    companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status")
        val USERNAME_KEY = stringPreferencesKey("username")
        val USER_ID_KEY = stringPreferencesKey("user_id")


        fun getInstance(context: Context): UserPreferences {
            return UserPreferences(context)
        }
    }


    suspend fun getAuthToken(): Flow<String> {
        return dataStore.data
            .map { preferences ->

                preferences[AUTH_TOKEN_KEY] ?: ""
            }
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun getLoginStatus(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                // Mengembalikan status login jika ada, atau false jika tidak ditemukan
                preferences[LOGIN_STATUS_KEY] ?: false
            }
    }

    suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = isLoggedIn
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
            preferences.remove(LOGIN_STATUS_KEY)
        }
    }

    suspend fun getUserName(): Flow<String> {
        return dataStore.data
            .map { preferences -> preferences[USERNAME_KEY] ?: "" }
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences -> preferences[USERNAME_KEY] = userName }
    }

    fun getUserId(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[USER_ID_KEY] ?: ""
            }
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }
}